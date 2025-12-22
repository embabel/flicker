package com.embabel.movie.domain

import com.embabel.agent.api.identity.User
import com.embabel.agent.api.identity.UserService
import com.embabel.movie.agent.OneThroughTen
import com.embabel.movie.service.OmdbClient
import org.slf4j.LoggerFactory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Spring Data repository for MovieBuff entities, which are mapped with JPA
 */
interface MovieBuffRepository : JpaRepository<MovieBuff, String> {

    fun findByDisplayName(displayName: String): MovieBuff?

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
) : UserService<MovieBuff> {

    private val logger = LoggerFactory.getLogger(MovieService::class.java)

    @Transactional(readOnly = true)
    override fun findByEmail(email: String): MovieBuff? {
        return movieBuffRepository.findById(email).orElse(null)
    }

    override fun findById(id: String): MovieBuff? {
        TODO("Not yet implemented")
    }

    override fun findByUsername(username: String): MovieBuff? {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun provisionUser(userInfo: User): MovieBuff {
        val movieBuff = MovieBuff(
            email = userInfo.email!!,
            username = userInfo.username,
            displayName = userInfo.displayName,
            countryCode = "au",
        )
        return movieBuffRepository.save(movieBuff).also {
            logger.info("Provisioned new movie buff: {}", it)
        }
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
        movieBuffRepository.findByDisplayName(name)


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
    fun updatePreferences(
        movieBuff: MovieBuff,
        about: String,
        movieLikes: String,
        movieDislikes: String,
        countryCode: String,
        streamingServices: List<StreamingService>,
    ) {
        val updatedMovieBuff = movieBuff.copy(
            about = about,
            movieLikes = movieLikes,
            movieDislikes = movieDislikes,
            countryCode = countryCode,
            streamingServices = streamingServices.toMutableList(),
        )
        movieBuffRepository.save(updatedMovieBuff)
        logger.info(
            "Updated preferences for user: {}, about={}, likes={}. dislikes={}, countryCode={}",
            movieBuff.email,
            updatedMovieBuff.about,
            updatedMovieBuff.movieLikes,
            updatedMovieBuff.movieDislikes,
            updatedMovieBuff.countryCode,
        )
    }

}