package com.embabel.movie.chatbot

import com.embabel.agent.api.annotation.Action
import com.embabel.agent.api.annotation.EmbabelComponent
import com.embabel.agent.api.common.ActionContext
import com.embabel.agent.api.common.OperationContext
import com.embabel.chat.AssistantMessage
import com.embabel.chat.Conversation
import com.embabel.chat.UserMessage
import com.embabel.movie.agent.DecoratedMovieBuff
import com.embabel.movie.domain.MovieBuff
import com.embabel.movie.service.AiHelpers


@EmbabelComponent
class ChatActions(
    private val properties: ChatbotProperties,
    private val aiHelpers: AiHelpers,
) {

    @Action
    fun analyzeTasteProfile(
        context: OperationContext,
    ): DecoratedMovieBuff {
        val user = context.user() as? MovieBuff ?: error("No user in context")
        val tasteProfile = aiHelpers.analyzeTasteProfile(user, context.ai())
        return DecoratedMovieBuff(
            movieBuff = user,
            tasteProfile = tasteProfile,
        )
    }

    @Action(
        trigger = UserMessage::class,
        canRerun = true,
    )
    fun respond(
        conversation: Conversation,
        decoratedMovieBuff: DecoratedMovieBuff,
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