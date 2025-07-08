package com.embabel.movie

import com.embabel.movie.domain.MovieBuffService
import com.embabel.movie.domain.rod
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent("Movie finder commands")
internal class MovieFinderShell(
    private val movieBuffService: MovieBuffService,
) {
    @ShellMethod
    fun users(
    ): String {
        println("Found ${movieBuffService.findAll().size} users")
        return movieBuffService.findAll().joinToString("\n") { it.toString() }
    }

    @ShellMethod
    fun createUser(): String {
        val user = movieBuffService.save(rod())
        return "Created user: ${user.name} with ID: ${user.id}"
    }
}