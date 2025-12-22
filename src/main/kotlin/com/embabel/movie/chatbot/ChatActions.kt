package com.embabel.movie.chatbot

import com.embabel.agent.api.annotation.Action
import com.embabel.agent.api.annotation.EmbabelComponent
import com.embabel.agent.api.common.ActionContext
import com.embabel.chat.AssistantMessage
import com.embabel.chat.Conversation
import com.embabel.chat.UserMessage

@EmbabelComponent
class ChatActions(
    private val properties: ChatbotProperties,
) {

    @Action(
        trigger = UserMessage::class,
    )
    fun respond(
        conversation: Conversation,
        context: ActionContext,
    ) {
        val assistantMessage: AssistantMessage = context.ai()
            .withLlm(properties.llm)
            .withTemplate("flicker")
            .respondWithSystemPrompt(
                conversation, mapOf(
                    "properties" to properties,
                    "voice" to properties.voice,
                    "objective" to properties.objective,
                )
            )
        context.sendMessage(
            conversation.addMessage(assistantMessage)
        )
    }
}