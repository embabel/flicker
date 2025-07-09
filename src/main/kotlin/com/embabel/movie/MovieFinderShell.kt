package com.embabel.movie

import com.embabel.movie.domain.MovieService
import org.springframework.shell.standard.ShellComponent

@ShellComponent("Movie finder commands")
internal class MovieFinderShell(
    private val movieService: MovieService,
) {
//    @ShellMethod
//    fun users(
//    ): String {
//        println("Found ${movieService.findAll().size} users")
//        return movieService.findAll().joinToString("\n") { it.toString() }
//    }
//
//    @ShellMethod
//    fun createUser(): String {
//        val user = movieService.save(rod(TODO()))
//        return "Created user: ${user.name} with email: ${user.email}"
//    }
}