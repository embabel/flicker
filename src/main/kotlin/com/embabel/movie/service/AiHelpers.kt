package com.embabel.movie.service

import com.embabel.agent.api.common.Ai
import com.embabel.movie.domain.MovieBuff
import org.springframework.stereotype.Component

data class TasteProfile(
    val summary: String,
)

/**
 * Various AI helper functions
 */
@Component
class AiHelpers {

    fun analyzeTasteProfile(movieBuff: MovieBuff, ai: Ai, tasteProfileWordCount: Int = 40): TasteProfile {
        return ai
            // TODO parameterize this
            .withAutoLlm()
            .creating(TasteProfile::class.java)
            .fromPrompt(
                """
            ${movieBuff.name} is a movie lover with hobbies of ${movieBuff.hobbies.joinToString(", ")}
            They have rated the following movies out of 10:
            ${movieBuff.randomRatings(50).joinToString("\n") { "${it.movie.title}: ${it.rating}" }}

            Return a summary of their taste profile as you understand it,
            in $tasteProfileWordCount words or less. Cover what they like and don't like.
            """.trimIndent()
            )
    }
}
