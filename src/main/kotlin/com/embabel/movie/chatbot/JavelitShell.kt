package com.embabel.movie.chatbot

import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent
class JavelitShell(private val javelitChatUI: JavelitChatUI) {

    @ShellMethod(value = "Launch web-based chat UI", key = ["uichat"])
    fun uichat(): String {
        if (javelitChatUI.isRunning()) {
            return "Chat UI is already running. Use 'uichat-stop' to stop it first."
        }
        val url = javelitChatUI.start()
        return "Chat UI started at $url\nOpen this URL in your browser to chat."
    }

    @ShellMethod(value = "Stop the web-based chat UI", key = ["uichat-stop"])
    fun uichatStop(): String {
        if (!javelitChatUI.isRunning()) {
            return "Chat UI is not running."
        }
        javelitChatUI.stop()
        return "Chat UI stopped."
    }

    @ShellMethod(value = "Check if web-based chat UI is running", key = ["uichat-status"])
    fun uichatStatus(): String {
        return if (javelitChatUI.isRunning()) {
            "Chat UI is running."
        } else {
            "Chat UI is not running. Use 'uichat' to start it."
        }
    }
}
