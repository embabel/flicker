package com.embabel.movie.domain

import com.embabel.agent.web.security.UserRepository
import com.embabel.movie.agent.OneThroughTen
import com.embabel.movie.service.OmdbClient
import org.slf4j.LoggerFactory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Spring Data repository for MovieBuff entities, which are mapped with JPA
 */
interface MovieBuffRepository : JpaRepository<MovieBuff, String>, UserRepository {

    fun findByName(name: String): MovieBuff?

}

interface MovieRepository : JpaRepository<Movie, String> {

    fun findByImdbId(imdbId: String): Movie?

    fun findByTitle(title: String): Movie?
}

@Service
class MovieService(
    private val movieBuffRepository: MovieBuffRepository,
    private val movieRepository: MovieRepository,
    private val omdbClient: OmdbClient,
) {

    private val logger = LoggerFactory.getLogger(MovieService::class.java)

    @Transactional(readOnly = true)
    fun findMovieBuffByEmail(email: String): MovieBuff? {
        return movieBuffRepository.findById(email).orElse(null)
    }

    @Transactional(readOnly = true)
    fun getUserRatings(movieBuff: MovieBuff, offset: Int, limit: Int): List<MovieRating> {
        return movieBuff.movieRatings
            .sortedBy { it.movie.title }
            .drop(offset)
            .take(limit)
    }

    @Transactional(readOnly = true)
    fun findMovieBuffByName(name: String): MovieBuff? =
        movieBuffRepository.findByName(name)


    @Transactional
    fun save(movieBuff: MovieBuff): MovieBuff {
        return movieBuffRepository.save(movieBuff)
    }

    @Transactional
    fun rate(movieBuff: MovieBuff, title: String, rating: OneThroughTen) {
        val movie = omdbClient.getMovieByTitle(title)
        if (movie == null) {
            logger.info("Movie not found for title{}", title)
            return
        }
        rate(movieBuff, movie, rating)
    }

    @Transactional
    fun rate(
        movieBuff: MovieBuff,
        movieInfo: MovieInfo,
        rating: OneThroughTen
    ) {
        val movie = movieRepository.findById(movieInfo.imdbId ?: error("Movie must have an IMDB ID"))
            .orElseGet { Movie(imdbId = movieInfo.imdbId, title = movieInfo.title) }
        movieRepository.save(movie)
        movieBuff.addRating(MovieRating(movie, rating))
        movieBuffRepository.save(movieBuff)
        logger.info("Recorded movie rating: {}, {}", movie.imdbId, movie.title)
    }

    @Transactional
    fun updatePreferences(movieBuff: MovieBuff, movieLikes: String, movieDislikes: String) {
        val updatedMovieBuff = movieBuff.copy(
            movieLikes = movieLikes,
            movieDislikes = movieDislikes
        )
        movieBuffRepository.save(updatedMovieBuff)
        logger.info(
            "Updated preferences for user: {}, likes={}. dislikes={}",
            movieBuff.email,
            updatedMovieBuff.movieLikes,
            updatedMovieBuff.movieDislikes,
        )
    }

}