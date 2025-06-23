package com.embabel.movie

import com.embabel.agent.domain.persistence.EntityFinderTools
import org.springframework.ai.tool.annotation.Tool

class MovieBuffFinderTools(private val movieBuffRepository: MovieBuffRepository) :
    EntityFinderTools<MovieBuff>(movieBuffRepository, MovieBuff::class.java) {

    @Tool(description = "Search for a MovieBuff by name")
    fun searchByName(name: String): String {
        val found = movieBuffRepository.findByName(name)
        if (found == null) return "No entity found with name '$name'"
        return "SUCCESS looking for name '$name': found entity with id: ${found.id}"
    }
}