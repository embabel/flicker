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
package com.embabel.movie.service

import com.embabel.agent.api.common.Ai
import com.embabel.agent.core.CoreToolGroups
import com.embabel.movie.domain.MovieBuff
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

data class StreamableMovie(
    val movie: MovieResponse,
    val availableStreamingOptions: List<StreamingOption>,
    val allStreamingOptions: List<StreamingOption>,
)

@Profile("!test")
@Component
class MovieLookup(
    private val omdbClient: OmdbClient,
    private val streamingAvailabilityClient: StreamingAvailabilityClient,
) {

    private val logger = LoggerFactory.getLogger(MovieLookup::class.java)

    fun findTrailer(title: String, year: String, ai: Ai): String {
        val pr = ai.withAutoLlm().withToolGroup(CoreToolGroups.WEB)
        val id = pr.generateText(
            """
            What is the YouTube id for the trailer of the movie $title ($year)?
            Return just the id, nothing else.
            """.trimIndent()
        ).trim()
        return id
    }

    fun lookUpMovies(titles: List<String>): List<MovieResponse> {
        logger.info(
            "Looking up movies: {}",
            titles.joinToString(", ")
        )
        return titles.mapNotNull { title ->
            omdbClient.getMovieByTitle(title)
        }
    }

    fun getStreamingOptions(imdbId: String, countryCode: String): List<StreamingOption> {
        return streamingAvailabilityClient.getShowStreamingIn(
            imdb = imdbId,
            country = countryCode,
        ).distinct()
    }

    fun canStream(movieBuff: MovieBuff, movie: MovieResponse): StreamableMovie? {
        return try {
            val allStreamingOptions = getStreamingOptions(movie.imdbId, movieBuff.countryCode)

            if (allStreamingOptions.isEmpty()) {
                logger.info(
                    "Movie {} not available in country {} - filtering it out",
                    movie.title,
                    movieBuff.countryCode,
                )
                return null
            }

            val availableToUser = allStreamingOptions.filter {
                it.service.name.lowercase() in movieBuff.streamingServices.map { s -> s.id.lowercase() }
            }

            logger.debug(
                "Movie {} available in [{}] on {}: {} can watch it on {}",
                movie.title,
                movieBuff.countryCode,
                allStreamingOptions.map { it.service.name }.sorted().joinToString(", "),
                movieBuff.name,
                availableToUser.map { "${it.service.name} at ${it.link}" }.sorted().joinToString(", ")
            )

            StreamableMovie(
                movie = movie,
                allStreamingOptions = allStreamingOptions,
                availableStreamingOptions = availableToUser,
            )
        } catch (e: Exception) {
            logger.warn("Error checking streaming availability for {}: {}", movie.title, e.message)
            null
        }
    }

    fun filterAlreadySeen(movies: List<MovieResponse>, movieBuff: MovieBuff): List<MovieResponse> {
        val seenImdbIds = movieBuff.movieRatings.map { it.movie.imdbId }.toSet()
        return movies.filterNot { it.imdbId in seenImdbIds }
    }
}
