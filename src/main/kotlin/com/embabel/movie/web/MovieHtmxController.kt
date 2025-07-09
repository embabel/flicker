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
import com.embabel.movie.agent.MovieRequest
import com.embabel.movie.domain.MovieBuffRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping(value = ["/", "/movie"])
class MovieHtmxController(
    private val agentPlatform: AgentPlatform,
    // TODO shouldn't pass in repository here
    private val movieBuffRepository: MovieBuffRepository,
) {

    private val logger = LoggerFactory.getLogger(MovieHtmxController::class.java)


    @GetMapping
    fun findMovies(model: Model): String {
        model.addAttribute(
            "movieRequest", MovieRequest(
                preference = "Find me something with Penelope Cruz",
            )
        )
        return "find-movies-form"
    }

    @PostMapping("/find-movies")
    fun findMovies(
        @ModelAttribute movieRequest: MovieRequest,
        model: Model
    ): String {

        // Convert form to domain objects
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
            // TODO fix this
            movieBuffRepository.findAll().single(),
        )

        model.addAttribute("movieRequest", movieRequest)
        GenericProcessingValues(
            agentProcess = agentProcess,
            pageTitle = "Finding Movies",
            detail = movieRequest.preference,
            resultModelKey = "movieRecommendations",
            successView = "movie-recommendations",
        ).addToModel(model)
        agentPlatform.start(agentProcess)
        return "common/processing"
    }
}

