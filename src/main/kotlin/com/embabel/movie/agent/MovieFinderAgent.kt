/*
 * Copyright 2024-2025 Embabel Software, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.embabel.movie.agent

import com.embabel.agent.api.annotation.*
import com.embabel.agent.api.common.OperationContext
import com.embabel.agent.api.common.create
import com.embabel.agent.api.common.createObject
import com.embabel.agent.config.models.OpenAiModels
import com.embabel.agent.core.CoreToolGroups
import com.embabel.agent.core.all
import com.embabel.agent.core.last
import com.embabel.agent.domain.library.HasContent
import com.embabel.agent.domain.library.RelevantNewsStories
import com.embabel.agent.event.ProgressUpdateEvent
import com.embabel.agent.prompt.persona.Persona
import com.embabel.common.ai.model.LlmOptions
import com.embabel.common.ai.model.ModelSelectionCriteria.Companion.byName
import com.embabel.movie.domain.MovieBuff
import com.embabel.movie.service.MovieResponse
import com.embabel.movie.service.OmdbClient
import com.embabel.movie.service.StreamingAvailabilityClient
import com.embabel.movie.service.StreamingOption
import com.fasterxml.jackson.annotation.JsonPropertyDescription
import org.slf4j.LoggerFactory
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Profile
import kotlin.math.min

typealias OneThroughTen = Int

data class MovieRequest(
    val preference: String,
)

data class DecoratedMovieBuff(
    val movieBuff: MovieBuff,
    val tasteProfile: String,
)

data class SuggestedMovieTitles(
    val titles: List<String>,
)

/**
 * Fleshed out with IMDB data
 */
data class SuggestedMovies(
    val movies: List<MovieResponse>,
)

/**
 * Movies that are available to stream
 */
data class StreamableMovies(
    val movies: List<StreamableMovie>,
)

/**
 * Movies the user can stream in their country, but not on their preferred streaming services.
 */
data class Fallbacks(
    val movies: MutableList<StreamableMovie> = mutableListOf(),
)

/**
 * A movie that's available to our movie buff
 */
data class StreamableMovie(
    val movie: MovieResponse,
    val availableStreamingOptions: List<StreamingOption>,
    val allStreamingOptions: List<StreamingOption>,
)

data class StreamableMovieWithTrailer(
    val movie: MovieResponse,
    val availableStreamingOptions: List<StreamingOption>,
    val allStreamingOptions: List<StreamingOption>,
    val trailerUrl: String,
)

data class StreamableMoviesWithTrailers(
    val movies: List<StreamableMovieWithTrailer>,
)


/**
 * Structured recommendations
 */
data class MovieRecommendations(
    val caption: String,
    val writeup: String,
    val movies: List<StreamableMovieWithTrailer>,
) : HasContent {

    override val content get() = writeup
}


val Roger = Persona(
    name = "Roger",
    persona = "A creative movie critic who channels the famous movie critic Roger Ebert",
    voice = "You write like Roger Ebert",
    objective =
        """
        Suggest movies that will extend as well as entertain the user.
        Share your love of cinema and inspire the user to watch, learn and think.
        """.trimIndent(),
)

@ConfigurationProperties(prefix = "embabel.movie")
data class MovieFinderConfig(
    val suggestionCount: Int = 5,
    val tasteProfileWordCount: Int = 40,
    val writeupWordCount: Int = 200,
    val suggesterPersona: Persona = Roger,
    val writerPersona: Persona = Roger,
    val model: String = OpenAiModels.GPT_41_MINI,
    val confirmMovieBuff: Boolean = true,
    val bailAfterSuggestions: Int = 40,
) {

    val llm = LlmOptions(
        criteria = byName(model),
    )
}

/**
 * MovieFinder is a comprehensive agent in the Embabel agentic framework that recommends personalized
 * movie suggestions to users based on their preferences, viewing history, and current interests.
 *
 * The agent demonstrates the orchestration of multiple actions in a workflow to achieve a complex goal:
 * 1. Identifying a user (MovieBuff) from the repository
 * 2. Analyzing their taste profile using LLM
 * 3. Finding relevant news stories to inspire topical recommendations
 * 4. Generating movie suggestions based on preferences and filtering for availability
 * 5. Creating a personalized writeup of recommendations
 *
 * This class showcases several key agentic patterns:
 * - Declarative workflow with @Action annotations and pre/post conditions
 * - Blackboard pattern for sharing data between actions
 * - Integration with external services (OMDB, streaming availability)
 * - LLM prompting with context and persona
 * - Progress tracking and event publishing
 */
@Profile("!test")
@Agent(
    description = "Find movies a person hasn't seen and may find interesting"
)
class MovieFinderAgent(
    private val omdbClient: OmdbClient,
    private val streamingAvailabilityClient: StreamingAvailabilityClient,
    private val config: MovieFinderConfig,
) {

    private val logger = LoggerFactory.getLogger(MovieFinderAgent::class.java)

    /**
     * Analyzes the taste profile of a MovieBuff using LLM to understand their preferences.
     *
     * This action demonstrates:
     * - LLM prompting with structured data
     * - Text generation for analysis
     * - Data enrichment pattern (decorating the MovieBuff with analysis)
     *
     * @param movieBuff The MovieBuff whose taste profile needs to be analyzed
     * @param context The action context for LLM operations
     * @return A DecoratedMovieBuff containing the original MovieBuff and their taste profile
     */
    @Action
    fun analyzeTasteProfile(
        movieBuff: MovieBuff,
        context: OperationContext,
    ): DecoratedMovieBuff {
        val tasteProfile = context.promptRunner(config.llm) generateText
                """
                ${movieBuff.name} is a movie lover with hobbies of ${movieBuff.hobbies.joinToString(", ")}
                They have rated the following movies out of 10:
                ${
                    movieBuff.randomRatings(50).joinToString("\n") {
                        "${it.movie.title}: ${it.rating}"
                    }
                }

                Return a summary of their taste profile as you understand it,
                in ${config.tasteProfileWordCount} words or less. Cover what they like and don't like.
                """.trimIndent()
        logger.info("Analyzed taste profile for {}:\n{}", movieBuff.name, tasteProfile)
        return DecoratedMovieBuff(
            movieBuff = movieBuff,
            tasteProfile = tasteProfile,
        )
    }

    @Action(toolGroups = [CoreToolGroups.WEB])
    fun findNewsStories(
        dmb: DecoratedMovieBuff,
        movieRequest: MovieRequest
    ): RelevantNewsStories =
        using(config.llm).createObject(
            """
            ${dmb.movieBuff.name} is a movie buff.
            Their hobbies are ${dmb.movieBuff.hobbies.joinToString(", ")}
            Their movie taste profile is ${dmb.tasteProfile}
            About them: "${dmb.movieBuff.about}"

            Consider the following specific request that may govern today's choice: '${movieRequest.preference}'

            Given this, use web tools and generate search queries
            to find 5 relevant news stories that might inspire
            a movie choice for them tonight.
            Don't look for movie news but general news that might interest them.
            If possible, look for news specific to the specific request.
            Country: ${dmb.movieBuff.countryCode}
            """.trimIndent()
        )

    /**
     * Core action that suggests movies based on user preferences and current context.
     *
     * This action demonstrates:
     * - Post-condition verification (haveEnoughMovies)
     * - Re-runnable actions (canRerun)
     * - Persona-based LLM prompting
     * - Blackboard data sharing (context += suggestedMovieTitles)
     * - Multi-stage processing (title generation → movie lookup → streaming availability)
     *
     * @param dmb The DecoratedMovieBuff with taste profile
     * @param context The action context
     * @return StreamableMovies containing available movie recommendations
     */
    @Action(
        post = [HAVE_ENOUGH_MOVIES],
        canRerun = true,
    )
    fun suggestMovies(
        movieRequest: MovieRequest,
        dmb: DecoratedMovieBuff,
        relevantNewsStories: RelevantNewsStories,
        context: OperationContext,
    ): StreamableMovies {
        val fallbacks = context.last<Fallbacks>() ?: run {
            val fb = Fallbacks()
            context += fb
            fb
        }
        // TODO what if there aren't enough fallbacks
        if (areWeDesperate(context)) {
            logger.info(
                "Desperate for movie suggestions, returning {} fallbacks: {}",
                fallbacks.movies.size,
                fallbacks.movies.joinToString(", ") { it.movie.title }
            )
            return StreamableMovies(
                movies = fallbacks.movies,
            )
        }

        val suggestedMovieTitles = context.promptRunner(
            config.llm.withTemperature(1.3),
            promptContributors = listOf(config.suggesterPersona),
        ).createObject<SuggestedMovieTitles>(
            """
            Suggest ${config.suggestionCount} movie titles that ${dmb.movieBuff.name} hasn't seen, but may find interesting.

            Consider the specific request: "${movieRequest.preference}"

            Use the information about their preferences from below:
            Their movie taste: "${dmb.tasteProfile}"

            Their hobbies: ${dmb.movieBuff.hobbies.joinToString()}
            About them: "${dmb.movieBuff.about}"

            Don't include the following movies, which they've seen (rating attached):
            ${
                dmb.movieBuff.movieRatings
                    .sortedBy { it.movie.title }
                    .joinToString("\n") {
                        "${it.movie.title}: ${it.rating}"
                    }
            }
            Don't include these movies we've already suggested:
            ${excludedTitles(context).joinToString("\n")}

            Consider also the following news stories for topical inspiration:
            ${relevantNewsStories.contribution()}
            """.trimIndent(),
        )
        // Be sure to bind the suggested movie titles to the blackboard
        context += suggestedMovieTitles
        val suggestedMovies = lookUpMovies(suggestedMovieTitles)
        return streamableMovies(
            movieBuff = dmb.movieBuff,
            suggestedMovies = suggestedMovies,
            fallbacks = fallbacks,
        )
    }

    private fun areWeDesperate(context: OperationContext): Boolean {
        val suggestions = context.all<SuggestedMovieTitles>().flatMap { it.titles }.size
        return suggestions >= config.bailAfterSuggestions
    }

    private fun lookUpMovies(suggestedMovieTitles: SuggestedMovieTitles): SuggestedMovies {
        logger.info(
            "Looking up streaming status of suggested movies {}",
            suggestedMovieTitles.titles.joinToString(", ")
        )
        val movies = suggestedMovieTitles.titles.mapNotNull { title ->
            omdbClient.getMovieByTitle(title)
        }
        return SuggestedMovies(movies)
    }

    private fun streamableMovies(
        movieBuff: MovieBuff,
        suggestedMovies: SuggestedMovies,
        fallbacks: Fallbacks,
    ): StreamableMovies {
        val streamableMovieList = suggestedMovies.movies
            // Sometimes the LLM ignores being told not to
            // include movies the user has seen
            .filterNot { movie ->
                movie.imdbId in movieBuff.movieRatings.map { it.movie.imdbId }
            }
            .mapNotNull { movie ->
                try {
                    val allStreamingOptions =
                        streamingAvailabilityClient.getShowStreamingIn(
                            imdb = movie.imdbId,
                            country = movieBuff.countryCode,
                        ).distinct()
                    val availableToUser = allStreamingOptions.filter {
                        (it.service.name.lowercase() in movieBuff.streamingServices.map { it.id.lowercase() })  //|| it.type == "free"
                    }
                    logger.debug(
                        "Movie {} available in [{}] on {}: {} can watch it free on {}",
                        movie.title,
                        movieBuff.countryCode,
                        allStreamingOptions.map { it.service.name }.sorted().joinToString(", "),
                        movieBuff.name,
                        availableToUser.map { "${it.service.name} at ${it.link}" }.sorted().joinToString(", ")
                    )
                    if (allStreamingOptions.isEmpty()) {
                        logger.info(
                            "Movie {} not available to {} in their country {} - filtering it out",
                            movie.title,
                            movieBuff.name,
                            movieBuff.countryCode,
                        )
                        null
                    } else {
                        val streamableMovie =
                            StreamableMovie(
                                movie = movie,
                                allStreamingOptions = allStreamingOptions,
                                availableStreamingOptions = availableToUser,
                            )
                        if (availableToUser.isNotEmpty()) {
                            streamableMovie
                        } else {
                            // Available to the user, but not on their preferred streaming services
                            fallbacks.movies.add(streamableMovie)
                            logger.info(
                                "Movie {} not available to {} on any of their streaming services and we're not yet in desperation mode {} - filtering it out",
                                movie.title,
                                movieBuff.name,
                                movieBuff.streamingServices.sortedBy { it.name }.joinToString(", ")
                            )
                            null
                        }
                    }
                } catch (_: Exception) {
                    null
                }
            }
        return StreamableMovies(streamableMovieList)
    }

    /**
     * Condition method that checks if we have enough movie recommendations.
     * @param context The process context to access the blackboard
     * @return Boolean indicating if we have enough movies
     */
    @Condition(name = HAVE_ENOUGH_MOVIES)
    fun haveEnoughMovies(context: OperationContext): Boolean {
        val allStreamableMovies = allStreamableMovies(context)
        logger.debug("Have {} streamable movies", allStreamableMovies.size)
        return allStreamableMovies.size >= config.suggestionCount
    }

    /**
     * Helper method to collect all streamable movies from the blackboard.
     * @param context The process context to access the blackboard
     * @return List of all unique StreamableMovie objects
     */
    private fun allStreamableMovies(
        context: OperationContext,
    ): List<StreamableMovie> {
        val streamableMovies = context
            .all<StreamableMovies>()
            .flatMap { it.movies }
            .distinctBy { it.movie.imdbId }
        context.processContext.onProcessEvent(
            ProgressUpdateEvent(
                agentProcess = context.agentProcess,
                name = "Streamable movies",
                current = min(streamableMovies.size, config.suggestionCount),
                total = config.suggestionCount,
            )
        )
        return streamableMovies
    }

    /**
     * Helper method to track movies that have already been suggested.
     *
     * This method demonstrates:
     * - Blackboard pattern for historical data
     * - Combining data from multiple sources
     * - Deduplication and sorting for consistent output
     *
     * @param context The process context to access the blackboard
     * @return List of movie titles that should be excluded from new suggestions
     */
    private fun excludedTitles(
        context: OperationContext,
    ): List<String> {
        return context
            .all<SuggestedMovieTitles>()
            .flatMap { it.titles } + allStreamableMovies(context).map { it.movie.title }
            .distinct()
            .sorted()
    }

    @Action(pre = [HAVE_ENOUGH_MOVIES])
    fun findTrailers(context: OperationContext): StreamableMoviesWithTrailers {
        val recommendedMovies = allStreamableMovies(context)
        return StreamableMoviesWithTrailers(
            movies = context.parallelMap(recommendedMovies, 15) { movie ->
                val trailerUrl = findTrailer(movie, context)
                StreamableMovieWithTrailer(
                    movie = movie.movie,
                    availableStreamingOptions = movie.availableStreamingOptions,
                    allStreamingOptions = movie.allStreamingOptions,
                    trailerUrl = trailerUrl,
                )
            }
        )
    }

    /**
     * Final action that creates a personalized writeup of movie recommendations.
     *
     * This action demonstrates:
     * - Precondition verification (haveEnoughMovies)
     * - Goal achievement annotation (@AchievesGoal)
     * - Persona-based content generation
     * - Rich content formatting (Markdown)
     * - Comprehensive data integration for the final output
     *
     * @param dmb The DecoratedMovieBuff with taste profile
     * @param streamableMovies The available movie recommendations
     * @param context The action context
     * @return SuggestionWriteup containing the formatted recommendations
     */
    @Action
    @AchievesGoal(description = "Recommend movies for a movie buff using what we know about them")
    fun writeUpSuggestions(
        dmb: DecoratedMovieBuff,
        streamableMovies: StreamableMoviesWithTrailers,
        context: OperationContext,
    ): MovieRecommendations {
        val writeup = context.promptRunner(
            llm = config.llm,
            promptContributors = listOf(config.suggesterPersona)
        ).create<MovieWriteup>(
            """
                Write up a recommendation of ${config.suggestionCount} movies in ${config.writeupWordCount} words
                for ${dmb.movieBuff.name}
                based on the following information:
                Their hobbies are ${dmb.movieBuff.hobbies.joinToString(", ")}
                Their movie taste profile is ${dmb.tasteProfile}
                A bit about them: "${dmb.movieBuff.about}"
                
                Include a CONCISE caption for the writeup. 
                It should not include the movie buff's name and be no more than 5 words.
                Include a link to the trailer for each movie, if available.

                The streamable movie recommendations are:
                ${
                streamableMovies.movies.joinToString("\n\n") {
                    """
                        ${it.movie.title} (${it.movie.year}): ${it.movie.imdbId}
                        Director: ${it.movie.director}
                        Actors: ${it.movie.actors}
                        ${it.movie.plot}
                        Trailer: ${it.trailerUrl}
                        FREE Streaming available to ${dmb.movieBuff.name} on ${
                        it.availableStreamingOptions.joinToString(
                            ", "
                        ) { "${it.service.name} at ${it.link}" }
                    }
                        All streaming options: ${it.allStreamingOptions.joinToString(", ") { "${it.service.name} at ${it.link}" }}
                        """.trimIndent()
                }
            }

                Format in HTML.
                Use unordered lists as appropriate.
                Start any headings at <h4>
                Include links to the movies on IMDB and the streaming service link(s) for each.
                List those with FREE streaming first and call that out.
                """.trimIndent())
        return MovieRecommendations(
            caption = writeup.caption,
            writeup = writeup.writeup,
            movies = streamableMovies.movies,
        )
    }

    private fun findTrailer(movie: StreamableMovie, context: OperationContext): String {
        val pr = context.promptRunner().withToolGroup(CoreToolGroups.WEB)
        val id = pr.generateText(
            """
                What is the YouTube id for the trailer of the movie ${movie.movie.title} (${movie.movie.year})?
                Return just the id, nothing else.               
                """.trimIndent()
        ).trim()
        return id
    }

    companion object {
        const val HAVE_ENOUGH_MOVIES = "haveEnoughMovies"
    }
}

private data class MovieWriteup(
    @field:JsonPropertyDescription("Caption for the writeup, to be used as a title")
    val caption: String,
    val writeup: String,
)