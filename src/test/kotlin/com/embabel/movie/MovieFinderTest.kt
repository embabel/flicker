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

import com.embabel.agent.test.unit.FakeOperationContext
import com.embabel.movie.agent.MovieFinderAgent
import com.embabel.movie.agent.MovieFinderConfig
import com.embabel.movie.domain.MovieBuff
import com.embabel.movie.domain.MovieBuffRepository
import com.embabel.movie.service.AiHelpers
import com.embabel.movie.service.TasteProfile
import io.mockk.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class MovieFinderTest {

    private fun testMovieBuff() = MovieBuff(
        email = "rod@example.com",
        username = "rod",
        displayName = "Rod Johnson",
        countryCode = "US",
        hobbies = listOf("hiking", "reading"),
        about = "Loves classic films"
    )

    @Nested
    inner class AnalyzeTasteProfile {

        @Test
        fun `test analyze taste profile`() {
            val repository = mockk<MovieBuffRepository>()
            every { repository.findAll() } returns listOf(testMovieBuff())
            val fakeContext = FakeOperationContext()
            val tasteProfile = TasteProfile("Loves noir and complex plots")
            val aiHelpers = mockk<AiHelpers>()
            val capturedMovieBuff = slot<MovieBuff>()
            every { aiHelpers.analyzeTasteProfile(capture(capturedMovieBuff), any(), any()) } returns tasteProfile
            val movieFinder =
                MovieFinderAgent(mockk(), aiHelpers, MovieFinderConfig())
            val movieBuff = repository.findAll().first()

            val dmb = movieFinder.analyzeTasteProfile(movieBuff, fakeContext)
            assertEquals(movieBuff, dmb.movieBuff)
            assertEquals(tasteProfile, dmb.tasteProfile)
            assertEquals(movieBuff, capturedMovieBuff.captured)
        }

    }
}
