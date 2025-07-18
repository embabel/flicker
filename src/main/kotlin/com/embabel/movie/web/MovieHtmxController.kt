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
package com.embabel.movie.web

import com.embabel.agent.core.AgentPlatform
import com.embabel.agent.core.ProcessOptions
import com.embabel.agent.core.Verbosity
import com.embabel.agent.web.htmx.GenericProcessingValues
import com.embabel.agent.web.security.EmbabelAuth2User
import com.embabel.movie.agent.MovieRequest
import com.embabel.movie.domain.MovieBuff
import com.embabel.movie.domain.MovieService
import com.embabel.movie.domain.StreamingServiceRepository
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping(value = ["/", "/movie"])
class MovieHtmxController(
    private val agentPlatform: AgentPlatform,
    private val movieService: MovieService,
    private val countryCodesProvider: CountryCodesProvider = DefaultCountryCodesProvider,
    private val streamingServiceRepository: StreamingServiceRepository,
) {

    private val logger = LoggerFactory.getLogger(MovieHtmxController::class.java)

    @GetMapping
    fun findMovies(
        model: Model,
        @AuthenticationPrincipal principal: OAuth2User,
    ): String {
        model.addAttribute(
            "movieRequest", MovieRequest(
                preference = "A film with a remarkable musical score",
            )
        )
        addCommonAttributes(model, movieBuff(principal))
        return "find-movies-form"
    }

    @PostMapping("/find-movies")
    fun findMovies(
        @ModelAttribute movieRequest: MovieRequest,
        model: Model,
        @AuthenticationPrincipal
        principal: OAuth2User,
    ): String {
        val movieBuff = movieBuff(principal)
        logger.info("Finding movies for user {} named {}", movieBuff.email, movieBuff.name)

        val agent = agentPlatform.agents().singleOrNull { it.name.lowercase().contains("movie") }
            ?: error("No  movie agent found. Please ensure the movie agent is registered.")

        val agentProcess = agentPlatform.createAgentProcessFrom(
            agent = agent,
            processOptions = ProcessOptions(
                verbosity = Verbosity(
                    showPrompts = true,
                    showLlmResponses = true,
                ),
            ),
            movieRequest,
            movieBuff,
        )

        model.addAttribute("movieRequest", movieRequest)
        addCommonAttributes(model, movieBuff)
        GenericProcessingValues(
            agentProcess = agentProcess,
            pageTitle = "Finding Movies",
            detail = movieRequest.preference,
            resultModelKey = "movieRecommendations",
            successView = "movie-recommendations",
            css = "/css/film_noir.css",
        ).addToModel(model)
        agentPlatform.start(agentProcess)
        return "common/processing"
    }

    /**
     * View all ratings for the current user
     */
    @GetMapping("/ratings")
    fun viewRatings(
        model: Model,
        @AuthenticationPrincipal principal: OAuth2User,
    ): String {
        val movieBuff = movieBuff(principal)
        val limit = 10
        val ratings = movieService.getUserRatings(movieBuff, 0, limit)

        addCommonAttributes(model, movieBuff)
        model.addAttribute("ratings", ratings)
        model.addAttribute("offset", ratings.size)
        model.addAttribute("limit", limit)
        model.addAttribute("hasMore", ratings.size < movieBuff.movieRatings.size)

        return "movie-ratings"
    }

    /**
     * Load more ratings for infinite scroll
     */
    @GetMapping("/ratings/more", produces = [MediaType.TEXT_HTML_VALUE])
    fun loadMoreRatings(
        @RequestParam offset: Int,
        @RequestParam limit: Int = 10,
        model: Model,
        @AuthenticationPrincipal principal: OAuth2User,
    ): String {
        val movieBuff = movieBuff(principal)
        logger.info("Loading more ratings for user {} at offset {}", movieBuff.email, offset)
        val ratings = movieService.getUserRatings(movieBuff, offset, limit)

        val newOffset = offset + ratings.size
        val totalRatings = movieBuff.movieRatings.size

        model.addAttribute("ratings", ratings)
        model.addAttribute("offset", newOffset)
        model.addAttribute("limit", limit)
        model.addAttribute("hasMore", newOffset < totalRatings)

        return "fragments/rating-items"
    }

    /**
     * Show form to add a new rating
     */
    @GetMapping("/ratings/add")
    fun showRatingForm(model: Model): String {
        return "add-rating-form"
    }

    /**
     * Process a new rating submission
     */
    @PostMapping("/ratings/add", produces = [MediaType.TEXT_HTML_VALUE])
    fun addRating(
        @RequestParam title: String,
        @RequestParam rating: Int,
        @AuthenticationPrincipal principal: OAuth2User,
        model: Model
    ): String {
        val movieBuff = movieBuff(principal)

        // Since OneThroughTen is just a typealias for Int, we can use the rating directly
        // after validating it's in the correct range
        if (rating !in 1..10) {
            error("Rating must be between 1 and 10")
        }

        movieService.rate(movieBuff, title, rating)
        logger.info("Added rating for movie '{}' with score {} by user {}", title, rating, movieBuff.email)

        // Return the newly added rating as HTML fragment
        val updatedMovieBuff = movieService.findByEmail(movieBuff.email)
            ?: error("Could not find movie buff after adding rating")

        val latestRating = updatedMovieBuff.movieRatings.maxByOrNull { it.timestamp }

        model.addAttribute("ratings", listOfNotNull(latestRating))
        model.addAttribute("offset", 1)  // Since we're only showing one rating
        model.addAttribute("limit", 10)
        model.addAttribute("hasMore", false)  // Don't show load more for the single new rating

        return "fragments/rating-items"
    }

    /**
     * Show form to edit preferences
     */
    @GetMapping("/preferences")
    fun showPreferencesForm(
        model: Model,
        @AuthenticationPrincipal principal: OAuth2User
    ): String {
        val movieBuff = movieBuff(principal)
        model.addAttribute("countryCodes", countryCodesProvider.countryCodes())
        model.addAttribute("streamingServices", streamingServiceRepository.findAll())
        addCommonAttributes(model, movieBuff)
        return "edit-preferences-form"
    }

    /**
     * Process preferences update
     */
    @PostMapping("/preferences")
    fun updatePreferences(
        @RequestParam about: String,
        @RequestParam movieLikes: String,
        @RequestParam movieDislikes: String,
        @RequestParam countryCode: String,
        @RequestParam streamingServices: List<String>,
        @AuthenticationPrincipal principal: OAuth2User,
    ): String {
        val movieBuff = movieBuff(principal)

        movieService.updatePreferences(
            movieBuff, about, movieLikes, movieDislikes,
            countryCode,
            streamingServices.map { id ->
                streamingServiceRepository.findById(id)
                    .orElseThrow { error("Streaming service with ID $id not found") }
            }
        )
        logger.info("Updated preferences for user {}", movieBuff.email)

        // Redirect to the movie finder page
        return "redirect:/movie"
    }

    private fun movieBuff(principal: OAuth2User): MovieBuff {
        val embabelUser = (principal as? EmbabelAuth2User)?.getUser() as? MovieBuff
            ?: error("User is not a movie buff. Please register as a movie buff to perform this action.")

        // Always fetch fresh data from database
        return movieService.findByEmail(embabelUser.email)
            ?: error("MovieBuff not found in database")
    }

    private fun addCommonAttributes(
        model: Model,
        movieBuff: MovieBuff,
    ) {
        model.addAttribute("movieBuff", movieBuff)
        model.addAttribute("pageTitle", "Movie Finder")
        model.addAttribute("user", movieBuff)
        model.addAttribute("css", "/css/film_noir.css")
    }
}


data class CountryCode(
    val code: String,
    val name: String,
)

interface CountryCodesProvider {
    fun countryCodes(): List<CountryCode>
}

object DefaultCountryCodesProvider : CountryCodesProvider {
    private val countryCodes = listOf(
        CountryCode("gb", "United Kingdom"),
        CountryCode("fr", "France"),
        CountryCode("de", "Germany"),
        CountryCode("jp", "Japan"),
        CountryCode("in", "India"),
        CountryCode("br", "Brazil"),
        CountryCode("ca", "Canada"),
        CountryCode("au", "Australia"),
        CountryCode("it", "Italy"),
        CountryCode("es", "Spain"),
        CountryCode("cn", "China"),
        CountryCode("kr", "South Korea"),
        CountryCode("mx", "Mexico"),
        CountryCode("za", "South Africa"),
        CountryCode("nl", "Netherlands"),
        CountryCode("se", "Sweden"),
        CountryCode("no", "Norway"),
        CountryCode("fi", "Finland"),
        CountryCode("dk", "Denmark"),
        CountryCode("pl", "Poland"),
        CountryCode("tr", "Turkey"),
        CountryCode("ar", "Argentina"),
        CountryCode("ch", "Switzerland"),
        CountryCode("be", "Belgium"),
        CountryCode("at", "Austria"),
        CountryCode("gr", "Greece"),
        CountryCode("pt", "Portugal"),
        CountryCode("hu", "Hungary"),
        CountryCode("cz", "Czech Republic"),
        CountryCode("ro", "Romania"),
        CountryCode("nz", "New Zealand"),
        CountryCode("us", "United States"),
    ).distinct().sortedBy { it.name }

    override fun countryCodes(): List<CountryCode> = countryCodes
}