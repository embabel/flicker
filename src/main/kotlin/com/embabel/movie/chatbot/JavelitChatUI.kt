package com.embabel.movie.chatbot

import com.embabel.agent.api.channel.MessageOutputChannelEvent
import com.embabel.agent.api.channel.OutputChannel
import com.embabel.agent.api.channel.OutputChannelEvent
import com.embabel.agent.api.identity.SimpleUser
import com.embabel.chat.AssistantMessage
import com.embabel.chat.Chatbot
import com.embabel.chat.Message
import com.embabel.chat.UserMessage
import io.javelit.core.Jt
import io.javelit.core.Server
import org.slf4j.LoggerFactory
import org.springframework.core.io.DefaultResourceLoader
import org.springframework.stereotype.Component
import java.awt.Desktop
import java.net.URI
import java.util.*
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.BlockingQueue
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicReference

@Component
class JavelitChatUI(
    private val chatbot: Chatbot,
    private val properties: ChatbotProperties,
) {
    private val logger = LoggerFactory.getLogger(JavelitChatUI::class.java)
    private val serverRef = AtomicReference<Server>()

    companion object {
        private val ANONYMOUS_USER = SimpleUser(
            "anonymous",
            "Anonymous User",
            "anonymous",
            null
        )
    }

    fun start(openBrowser: Boolean = true): String {
        val port = properties.uiPort
        if (serverRef.get() != null) {
            return "Chat UI already running at http://localhost:$port"
        }

        val headersFilePath = resolveCssPath()
        val serverBuilder = Server.builder(this::app, port)
        if (headersFilePath != null) {
            logger.info("Using custom CSS from: {}", headersFilePath)
            serverBuilder.headersFile(headersFilePath)
        }
        val server = serverBuilder.build()
        server.start()
        serverRef.set(server)

        val url = "http://localhost:$port"
        logger.info("Javelit Chat UI started at {}", url)

        if (openBrowser) {
            openInBrowser(url)
        }

        return url
    }

    fun stop() {
        val server = serverRef.getAndSet(null)
        if (server != null) {
            server.stop()
            logger.info("Javelit Chat UI stopped")
        }
    }

    fun isRunning(): Boolean = serverRef.get() != null

    @Suppress("UNCHECKED_CAST")
    private fun app() {
        val sessionState = Jt.sessionState()

        val displayHistory = sessionState.computeIfAbsent("displayHistory") {
            mutableListOf<Message>()
        } as MutableList<Message>

        val chatSession = sessionState.computeIfAbsent("chatSession") {
            val queue = ArrayBlockingQueue<Message>(10)
            val outputChannel = QueueingOutputChannel(queue)
            val session = chatbot.createSession(ANONYMOUS_USER, outputChannel, UUID.randomUUID().toString())
            sessionState["responseQueue"] = queue
            session
        } as com.embabel.chat.ChatSession

        val responseQueue = sessionState["responseQueue"] as BlockingQueue<Message>

        Jt.title(":movie_camera: Flicker Chat").use()
        Jt.markdown("_Your AI movie recommendation assistant_").key("subtitle").use()
        Jt.markdown("---").key("header-divider").use()

        val msgContainer = Jt.container().use()

        for (i in displayHistory.indices) {
            val message = displayHistory[i]
            when (message) {
                is UserMessage -> Jt.markdown(":bust_in_silhouette: **You:** ${message.content}")
                    .key("msg-$i")
                    .use(msgContainer)
                is AssistantMessage -> Jt.markdown(":robot: **Flicker:** ${message.content}")
                    .key("msg-$i")
                    .use(msgContainer)
                else -> {} // Ignore system messages
            }
        }

        val inputMessage = Jt.textInput("Your message:").use()

        if (!inputMessage.isNullOrBlank()) {
            val msgIndex = displayHistory.size

            val userMessage = UserMessage(inputMessage)
            displayHistory.add(userMessage)
            Jt.markdown(":bust_in_silhouette: **You:** $inputMessage")
                .key("msg-$msgIndex")
                .use(msgContainer)

            try {
                chatSession.onUserMessage(userMessage)

                val response = responseQueue.poll(60, TimeUnit.SECONDS)
                if (response != null) {
                    displayHistory.add(response)
                    Jt.markdown(":robot: **Flicker:** ${response.content}")
                        .key("msg-${msgIndex + 1}")
                        .use(msgContainer)
                } else {
                    Jt.warning("Response timed out").use(msgContainer)
                }
            } catch (e: Exception) {
                logger.error("Error getting chatbot response", e)
                Jt.error("Error: ${e.message}").use(msgContainer)
            }
        }

        Jt.markdown("---").key("footer-divider").use()
        Jt.markdown("_Powered by Embabel Agent_").key("footer-text").use()
    }

    private fun openInBrowser(url: String) {
        try {
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(URI(url))
                logger.info("Opened browser at {}", url)
                return
            }
        } catch (e: Exception) {
            logger.debug("Desktop API failed: {}", e.message)
        }

        try {
            val os = System.getProperty("os.name").lowercase()
            val pb = when {
                os.contains("mac") -> ProcessBuilder("open", url)
                os.contains("win") -> ProcessBuilder("cmd", "/c", "start", url)
                else -> ProcessBuilder("xdg-open", url)
            }
            pb.start()
            logger.info("Opened browser at {}", url)
        } catch (e: Exception) {
            logger.warn("Failed to open browser: {}. Please open manually: {}", e.message, url)
        }
    }

    private fun resolveCssPath(): String? {
        return try {
            val resource = DefaultResourceLoader().getResource(properties.uiCssPath)
            resource.file.absolutePath
        } catch (e: Exception) {
            try {
                val resource = DefaultResourceLoader().getResource(properties.uiCssPath)
                val tempFile = java.io.File.createTempFile("javelit-css-", ".html")
                tempFile.deleteOnExit()
                resource.inputStream.use { input ->
                    java.io.FileOutputStream(tempFile).use { output ->
                        input.transferTo(output)
                    }
                }
                logger.info("Extracted CSS to temp file: {}", tempFile.absolutePath)
                tempFile.absolutePath
            } catch (ex: Exception) {
                logger.warn("Failed to resolve CSS path {}: {}", properties.uiCssPath, ex.message)
                null
            }
        }
    }

    private class QueueingOutputChannel(private val queue: BlockingQueue<Message>) : OutputChannel {
        override fun send(event: OutputChannelEvent) {
            if (event is MessageOutputChannelEvent) {
                val msg = event.message
                if (msg is AssistantMessage) {
                    queue.offer(msg)
                }
            }
        }
    }
}
