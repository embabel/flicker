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

import com.embabel.agent.api.common.OperationContext
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class MovieFinderTest {

    @Nested
    inner class AnalyzeTasteProfile {

        @Test
        fun `test analyze taste profile`() {
            val repository: MovieBuffRepository = TODO()
            val mockOperationContext = mockk<OperationContext>()
            val prompt = slot<String>()
            every { mockOperationContext.promptRunner(any()).generateText(capture(prompt)) } returns "test"
            val movieFinder =
                MovieFinderAgent(mockk(), mockk(), repository, MovieFinderConfig())
            val movieBuff = repository.findAll().first()

            val dmb = movieFinder.analyzeTasteProfile(movieBuff, mockOperationContext)
            assertEquals(movieBuff, dmb.movieBuff)
            assertTrue(
                prompt.captured.contains(movieBuff.name),
                "Prompt should contain movie buff name '${movieBuff.name}': ${prompt.captured}"
            )
            movieBuff.hobbies.forEach { hobby ->
                assertTrue(
                    prompt.captured.contains(hobby),
                    "Prompt should contain movie buff about '${movieBuff.about}': ${prompt.captured}"
                )
            }
        }

    }
}
