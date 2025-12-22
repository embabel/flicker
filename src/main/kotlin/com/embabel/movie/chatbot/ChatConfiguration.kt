package com.embabel.movie.chatbot

import com.embabel.agent.core.AgentPlatform
import com.embabel.agent.core.Verbosity
import com.embabel.chat.Chatbot
import com.embabel.chat.agent.AgentProcessChatbot
import com.embabel.common.ai.model.LlmOptions
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

data class Voice(
    val persona: String = "roger",
    val maxWords: Int = 250,
)

@ConfigurationProperties(prefix = "flicker.chat")
data class ChatbotProperties(
    val uiPort: Int = 8889,
    val uiCssPath: String = "classpath:ui/chat.css",
    val llm: LlmOptions = LlmOptions(),
    val voice: Voice = Voice(),
    val objective: String = "movies",
)

@Configuration
@EnableConfigurationProperties(ChatbotProperties::class)
class ChatConfiguration {

    @Bean
    fun chatbot(agentPlatform: AgentPlatform): Chatbot {
        return AgentProcessChatbot.utilityFromPlatform(
            agentPlatform,
            Verbosity().showPrompts()
        )
    }
}
