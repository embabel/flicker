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
import com.embabel.movie.domain.*
import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DataPopulatorService(
    private val movieService: MovieService,
    private val movieBuffRepository: MovieBuffRepository,
    private val streamingServiceRepository: StreamingServiceRepository,
) {
    private val logger = LoggerFactory.getLogger(DataPopulatorService::class.java)

    @Transactional
    fun populate() {
        try {
            logger.info(
                "{} streaming services, {} movie buffs found in database.",
                streamingServiceRepository.count(),
                movieBuffRepository.count(),
            )
            // Skip if data already exists
            if (streamingServiceRepository.count() > 0 || movieBuffRepository.count() > 0) {
                return
            }

            logger.info("Populating initial data...")

            val netflix = StreamingService("Netflix", "https://www.netflix.com")
            streamingServiceRepository.save(netflix)
            val stan = StreamingService("Stan", "https://www.stan.com.au")
            streamingServiceRepository.save(stan)
            val disneyPlus = StreamingService("Disney+", "https://www.disneyplus.com")
            streamingServiceRepository.save(disneyPlus)

            val name = "Rod"
            if (movieService.findMovieBuffByName(name) == null) {
                val rod = MovieBuff(
                    username = "Rod",
                    displayName = "Rod Johnson",
                    email = "johnsonroda@gmail.com",
                    hobbies = listOf("Travel", "Skiing", "Chess", "Hiking", "Reading"),
                    countryCode = "au",
                    about = """
                    Rod is an Australian man who has a PhD in Musicology and
                    has a career as a software engineer, author and tech entrepreneur.
                    He is widely traveled and has lived in California and the UK
                    before returning to Sydney.
                """.trimIndent(),
                    streamingServices = mutableListOf(netflix, stan, disneyPlus),
                    movieLikes = "Complex plots, film noir",
                    movieDislikes = "Predictable endings, formulaic blockbusters, anime",
                )
                loadRatings(rod, "movie/rod_ratings.tsv")
                movieService.save(rod)
            }
            logger.info("Data population completed successfully")
        } catch (e: Exception) {
            logger.error("Data population failed - transaction will rollback", e)
            throw e
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
                try {
                    movieService.rate(movieBuff, title, rating)
                } catch (e: Exception) {
                    logger.warn("Failed to rate movie '{}' with rating {}: {}", title, rating, e.message)
                }
            }
    }
}

@Component
class StartupDataInitializer(
    private val dataPopulatorService: DataPopulatorService,
) : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        dataPopulatorService.populate()
    }
}
