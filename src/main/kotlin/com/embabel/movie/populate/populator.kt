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
import com.embabel.movie.domain.MovieBuff
import com.embabel.movie.domain.MovieService
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class StartupDataInitializer(
    private val movieService: MovieService,
) {

    @PostConstruct
    fun init() {
        val name = "Rod"
        if (movieService.findMovieBuffByName(name) == null) {
            val rod = MovieBuff(
                name = "Rod",
                email = "johnsonroda@gmail.com",
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
            loadRatings(rod, "movie/rod_ratings.tsv")
            movieService.save(rod)
        }
    }


    fun loadRatings(movieBuff: MovieBuff, tsv: String) {
        addRatings(
            movieBuff,
            RandomFromFileMessageGenerator(tsv)
                .messages,
        )
    }


    fun addRatings(
        movieBuff: MovieBuff,
        inputs: List<String>,
    ) {
        inputs
            .forEach { line ->
                // Split on multiple spaces and filter out empty strings
                val parts = line.split("\\s+".toRegex()).filter { it.isNotEmpty() }

                // Last element is the rating, everything else is the title
                val rating = parts.last().toInt()
                val title = parts.dropLast(1).joinToString(" ")
                movieService.rate(movieBuff, title, rating)
            }
    }
}