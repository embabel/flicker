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
package com.embabel.movie.populate

import com.embabel.common.util.RandomFromFileMessageGenerator
import com.embabel.common.util.loggerFor
import com.embabel.movie.domain.Movie
import com.embabel.movie.domain.MovieBuff
import com.embabel.movie.domain.MovieBuffRepository
import com.embabel.movie.domain.MovieRating
import com.embabel.movie.service.OmdbClient
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class StartupDataInitializer(
    private val movieBuffRepository: MovieBuffRepository,
    private val omdbClient: OmdbClient,
) {

    @PostConstruct
    fun init() {
        val name = "Rod"
        if (movieBuffRepository.findByName(name) == null) {
            movieBuffRepository.save(rod(omdbClient))
        }
    }
}

fun rod(omdbClient: OmdbClient) = MovieBuff(
    name = "Rod",
    email = "johnsonroda@gmail.com",
    movieRatings = parseRatings(
        RandomFromFileMessageGenerator("movie/rod_ratings.tsv")
            .messages,
        omdbClient,
    ),
    hobbies = listOf("Travel", "Skiing", "Chess", "Hiking", "Reading"),
    countryCode = "au",
    about = """
                Rod is an Australian man who has a PhD in Musicology and
                has a career as a software engineer, author and tech entrepreneur.
                He is widely traveled and has lived in California and the UK
                before returning to Sydney.
            """.trimIndent(),
    streamingServices = listOf("Netflix", "Stan", "Disney+")
)


fun parseRatings(inputs: List<String>, omdbClient: OmdbClient): List<MovieRating> {
    return inputs
        .mapNotNull { line ->
            // Split on multiple spaces and filter out empty strings
            val parts = line.split("\\s+".toRegex()).filter { it.isNotEmpty() }

            // Last element is the rating, everything else is the title
            val rating = parts.last().toInt()
            val title = parts.dropLast(1).joinToString(" ")
            val movie = omdbClient.getMovieByTitle(title)
            if (movie == null) {
                loggerFor<OmdbClient>().info("Movie not found for title{}", title)
                return@mapNotNull null
            }
            loggerFor<OmdbClient>().info("Movie found: {}, {}", movie.imdbId, movie.title)
            MovieRating(movie = Movie(movie), rating = rating)
        }
}
