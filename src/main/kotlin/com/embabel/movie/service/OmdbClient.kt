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

import com.embabel.common.util.loggerFor
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient

/**
 * See https://www.omdbapi.com/
 * Obtain an API key here: https://www.omdbapi.com/apikey.aspx
 */
@Component
@ConditionalOnProperty("OMDB_API_KEY")
class OmdbClient(
    private val apiKey: String = System.getenv("OMDB_API_KEY"),
    private val objectMapper: ObjectMapper,
) {

    private val omdbRestClient: RestClient = run {
        RestClient.builder()
            .baseUrl("http://omdbapi.com")
            .defaultHeader("Accept", "application/json")
            .build()
    }


    fun getMovieById(imdb: String): MovieResponse {
        return omdbRestClient.get()
            .uri { uriBuilder ->
                uriBuilder
                    .queryParam("apikey", apiKey)
                    .queryParam("i", imdb)
                    .build()
            }
            .retrieve()
            .body(MovieResponse::class.java)
            ?: throw RuntimeException("Failed to fetch movie data")
    }

    fun getMovieByTitle(title: String): MovieResponse? {
        return try {
            val rawResponse = omdbRestClient.get()
                .uri { uriBuilder ->
                    uriBuilder
                        .queryParam("apikey", apiKey)
                        .queryParam("t", title)
                        .build()
                }
                .retrieve()
                .body(String::class.java)

            // This is so flaky that we log the raw response for debugging
            try {
                objectMapper.readValue(rawResponse, MovieResponse::class.java)
            } catch (e: Exception) {
                loggerFor<OmdbClient>().warn(
                    "Error response for title: {}. Raw response: {}",
                    title,
                    rawResponse
                )
                null
            }
        } catch (e: Exception) {
            // This API can be flaky, so we log the error and return null
            loggerFor<OmdbClient>().warn("Failed to fetch movie by title: $title", e)
            null
        }
    }

}

data class MovieResponse(
    @field:JsonProperty("Title") val title: String,
    @field:JsonProperty("Year") val year: String,
    @field:JsonProperty("Rated") val rated: String,
    @field:JsonProperty("Released") val released: String,
    @field:JsonProperty("Runtime") val runtime: String,
    @field:JsonProperty("Genre") val genre: String,
    @field:JsonProperty("Director") val director: String,
    @field:JsonProperty("Writer") val writer: String,
    @field:JsonProperty("Actors") val actors: String,
    @field:JsonProperty("Plot") val plot: String,
    @field:JsonProperty("Language") val language: String,
    @field:JsonProperty("Country") val country: String,
    @field:JsonProperty("Awards") val awards: String,
    @field:JsonProperty("Poster") val poster: String,
    @field:JsonProperty("Ratings") val ratings: List<Rating>,
    @field:JsonProperty("Metascore") val metascore: String,
    @field:JsonProperty("imdbRating") val imdbRating: String,
    @field:JsonProperty("imdbVotes") val imdbVotes: String,
    @field:JsonProperty("imdbID") val imdbId: String,
    @field:JsonProperty("Type") val type: String,
    @field:JsonProperty("DVD") val dvd: String?,
    @field:JsonProperty("BoxOffice") val boxOffice: String?,
    @field:JsonProperty("Production") val production: String?,
    @field:JsonProperty("Website") val website: String?,
    @field:JsonProperty("Response") val response: String
)

data class Rating(
    @field:JsonProperty("Source") val source: String,
    @field:JsonProperty("Value") val value: String
)

data class ErrorResponse(
    @field:JsonProperty("Error") val error: String
)
