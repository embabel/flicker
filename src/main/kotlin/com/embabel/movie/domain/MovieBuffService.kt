package com.embabel.movie.domain

import com.embabel.agent.web.security.UserRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Spring Data repository for MovieBuff entities, which are mapped with JPA
 */
interface MovieBuffRepository : JpaRepository<MovieBuff, String>, UserRepository {

    fun findByName(name: String): MovieBuff?


}

@Service
class MovieBuffService(
    private val movieBuffRepository: MovieBuffRepository,
) {

    @Transactional
    fun save(movieBuff: MovieBuff): MovieBuff {
        return movieBuffRepository.save(movieBuff)
    }

    @Transactional(readOnly = true)
    fun findAll(): List<MovieBuff> {
        return movieBuffRepository.findAll()
    }
}