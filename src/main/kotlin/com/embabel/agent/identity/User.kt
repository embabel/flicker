package com.embabel.agent.identity

import com.embabel.agent.domain.library.Person
import org.springframework.security.oauth2.core.OAuth2AuthenticationException

/**
 * Superinterface for all users in the system.
 */
interface User : Person {
    val email: String
}

interface UserService<U : User> {

    fun findByEmail(email: String): U?

    /**
     * Add the user to the system.
     * Default implementation refuses to do so.
     */
    fun provisionUser(email: String, name: String): U {
        throw OAuth2AuthenticationException("User not authorized: $email")
    }
}