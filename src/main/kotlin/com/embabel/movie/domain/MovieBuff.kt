package com.embabel.movie.domain

import com.embabel.agent.identity.User
import com.embabel.agent.prompt.persona.Persona
import com.embabel.common.core.types.Timestamped
import com.embabel.movie.agent.OneThroughTen
import jakarta.persistence.*
import org.hibernate.annotations.Immutable
import org.springframework.data.jpa.repository.JpaRepository
import java.time.Instant

@Entity
data class MovieBuff(
    @Id
    override val email: String,
    override val name: String,
    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    val movieRatings: MutableList<MovieRating> = mutableListOf(),
    val countryCode: String,
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
        name = "movie_buff_hobbies",
        joinColumns = [JoinColumn(name = "movie_buff_id")],
    )
    @Column(name = "hobby")
    val hobbies: List<String> = emptyList(),
    val movieLikes: String = "",
    val movieDislikes: String = "",
    val about: String = "",
    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    val streamingServices: MutableList<StreamingService> = mutableListOf(),
) : User {

    fun addRating(movieRating: MovieRating) {
        // Update any existing rating
        movieRatings.removeIf { it.movie.imdbId == movieRating.movie.imdbId }
        movieRatings.add(movieRating)
    }

    /**
     * We use this so we don't overwhelm the prompt
     */
    fun randomRatings(n: Int): List<MovieRating> {
        return movieRatings.shuffled().take(n)
    }
}

@Entity
data class MovieRating(
    @ManyToOne(fetch = FetchType.EAGER)
    val movie: Movie,
    val rating: OneThroughTen,
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private val id: String? = null,
    override val timestamp: Instant = Instant.now(),
) : Timestamped

interface MovieInfo {
    val imdbId: String?
    val title: String
}

@Entity
@Immutable
data class Movie(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private val id: String? = null,
    override val imdbId: String?,
    override val title: String,
) : MovieInfo {

    constructor(movieInfo: MovieInfo) : this(
        imdbId = movieInfo.imdbId,
        title = movieInfo.title,
    )
}

@Entity
@Immutable
data class StreamingService(
    @Id
    val id: String,
    val url: String,
    val name: String = id,
)

interface StreamingServiceRepository : JpaRepository<StreamingService, String>

/**
 * Roger Ebert style movie guide persona.
 */
@Entity
data class MovieGuide(
    @Id
    override val name: String,
    override val persona: String,
    override val voice: String,
    override val objective: String,
    override val role: String?,
) : Persona