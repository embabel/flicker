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
package com.embabel.movie

import com.embabel.agent.api.annotation.support.AgentMetadataReader
import com.embabel.agent.core.Action
import com.embabel.agent.core.Agent
import com.embabel.agent.core.AgentProcess
import com.embabel.agent.core.AgentProcessStatusCode
import com.embabel.agent.domain.io.UserInput
import com.embabel.agent.domain.persistence.support.FinderInvocation
import com.embabel.agent.domain.persistence.support.FinderInvocations
import com.embabel.agent.spi.LlmInteraction
import com.embabel.agent.testing.integration.DummyObjectCreatingLlmOperations
import com.embabel.agent.testing.integration.IntegrationTestUtils.dummyAgentPlatform
import com.embabel.common.util.DummyInstanceCreator
import com.embabel.movie.agent.MovieFinderAgent
import com.embabel.movie.agent.MovieFinderConfig
import com.embabel.movie.domain.MovieBuffRepository
import com.embabel.movie.domain.rod
import com.embabel.movie.service.MovieResponse
import com.embabel.movie.service.OmdbClient
import com.embabel.movie.service.StreamingAvailabilityClient
import com.embabel.movie.service.StreamingOption
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

class MovieFinderEndToEndTest {

    class FinderAwareLlmOperations : DummyObjectCreatingLlmOperations(DummyInstanceCreator.LoremIpsums) {
        override fun <O> createObject(
            prompt: String,
            interaction: LlmInteraction,
            outputClass: Class<O>,
            agentProcess: AgentProcess,
            action: Action?
        ): O {
            if (prompt.contains("Spring Data")) {
                return FinderInvocations(
                    invocations = listOf(
                        FinderInvocation(
                            name = "findById",
                            args = listOf("Rod")
                        )
                    )
                ) as O
            } else {
                return super.createObject(prompt, interaction, outputClass, agentProcess, action)
            }
        }
    }

    @Test
    fun `test run process`() {
        val ap = dummyAgentPlatform(FinderAwareLlmOperations())
        val mockOmdbClient = mockk<OmdbClient>()
        every { mockOmdbClient.getMovieByTitle(any()) } answers {
            DummyInstanceCreator.BigLebowski.createDummyInstance(
                MovieResponse::class.java
            )
        }
        val movieBuffRepository = mockk<MovieBuffRepository>()
        every { movieBuffRepository.findById(any()) } returns Optional.of(rod())

        val mockStreamingAvailabilityClient = mockk<StreamingAvailabilityClient>()
        val mockStreamingOption = mockk<StreamingOption>()
        every { mockStreamingOption.service.name } returns "Netflix"
        every { mockStreamingOption.link } returns "https://www.netflix.com"
        every { mockStreamingAvailabilityClient.getShowStreamingIn(any(), any()) } answers {
            // 2/3 of the time it will not return a streaming option
            buildList {
                repeat(2) {
                    add(emptyList())
                }
                add(listOf(mockStreamingOption))
            }.random()
        }

        val mf = MovieFinderAgent(
            mockOmdbClient, mockStreamingAvailabilityClient, movieBuffRepository,
            MovieFinderConfig(confirmMovieBuff = false),
        )
        val movieAgent = AgentMetadataReader().createAgentMetadata(mf) as Agent
        val result = ap.runAgentWithInput(
            agent = movieAgent,
            input = UserInput("Rod wants to watch a movie with his friends"),
        )
        assertEquals(AgentProcessStatusCode.COMPLETED, result.status, "Unexpected status: ${result.failureInfo}")
    }

    @Test
    fun `test kills process that isn't terminating`() {
        val ap = dummyAgentPlatform(llmOperations = FinderAwareLlmOperations())
        val mockOmdbClient = mockk<OmdbClient>()
        every { mockOmdbClient.getMovieByTitle(any()) } returns DummyInstanceCreator.BigLebowski.createDummyInstance(
            MovieResponse::class.java
        )
        val movieBuffRepository = mockk<MovieBuffRepository>()
        every { movieBuffRepository.findById(any()) } returns Optional.of(rod())
        val mockStreamingAvailabilityClient = mockk<StreamingAvailabilityClient>()
        every { mockStreamingAvailabilityClient.getShowStreamingIn(any(), any()) } returns emptyList()
        val mf = MovieFinderAgent(
            mockOmdbClient, mockStreamingAvailabilityClient, movieBuffRepository,
            MovieFinderConfig(confirmMovieBuff = false)
        )
        val movieAgent = AgentMetadataReader().createAgentMetadata(mf) as Agent
        val result = ap.runAgentWithInput(
            agent = movieAgent,
            input = UserInput("Rod wants to watch a movie with his friends"),
        )
        assertEquals(AgentProcessStatusCode.TERMINATED, result.status, "Unexpected status: ${result.failureInfo}")
    }

    @Test
    fun `test awaits movie buff confirmation`() {
        val ap = dummyAgentPlatform(llmOperations = FinderAwareLlmOperations())
        val mockOmdbClient = mockk<OmdbClient>()
        every { mockOmdbClient.getMovieByTitle(any()) } returns DummyInstanceCreator.BigLebowski.createDummyInstance(
            MovieResponse::class.java
        )
        val movieBuffRepository = mockk<MovieBuffRepository>()
        every { movieBuffRepository.findById(any()) } returns Optional.of(rod())
        val mockStreamingAvailabilityClient = mockk<StreamingAvailabilityClient>()
        every { mockStreamingAvailabilityClient.getShowStreamingIn(any(), any()) } returns emptyList()
        val mf = MovieFinderAgent(
            mockOmdbClient, mockStreamingAvailabilityClient, movieBuffRepository,
            MovieFinderConfig()
        )
        val movieAgent = AgentMetadataReader().createAgentMetadata(mf) as Agent
        val result = ap.runAgentWithInput(
            agent = movieAgent,
            input = UserInput("Rod wants to watch a movie with his friends"),
        )
        assertEquals(AgentProcessStatusCode.WAITING, result.status, "Unexpected status: ${result.failureInfo}")
    }
}
