package com.embabel.movie.domain

import com.embabel.agent.prompt.persona.Persona
import com.embabel.agent.web.security.User
import com.embabel.movie.agent.OneThroughTen
import jakarta.persistence.*
import org.hibernate.annotations.Immutable

@Entity
data class MovieBuff(
    @Id
    override val email: String,
    override val name: String,
    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    val movieRatings: List<MovieRating>,
    val countryCode: String,
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
        name = "movie_buff_hobbies",
        joinColumns = [JoinColumn(name = "movie_buff_id")],
    )
    @Column(name = "hobby")
    val hobbies: List<String>,
    val about: String,
    val streamingServices: List<String>,
) : User {

    /**
     * We use this so we don't overwhelm the prompt
     */
    fun randomRatings(n: Int): List<MovieRating> {
        return movieRatings.shuffled().take(n)
    }
}

@Entity
data class MovieRating(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private val id: String? = null,
    val rating: OneThroughTen,
    @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    val movie: Movie,
)

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
data class MovieGuide(
    override val name: String,
    override val persona: String,
    override val voice: String,
    override val objective: String,
    override val role: String?,
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private val id: String? = null,
) : Persona