package com.embabel.agent.web.security

import com.embabel.agent.api.identity.SimpleUser
import com.embabel.agent.api.identity.User
import com.embabel.agent.api.identity.UserService
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.OAuth2AuthenticationException
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class EmbabelOAuth2UserService(
    private val userService: UserService<User>,
) : DefaultOAuth2UserService() {

    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val user = super.loadUser(userRequest)

        // Extract email from Google OAuth2 response
        val email = user.attributes["email"] as? String
            ?: throw OAuth2AuthenticationException("Email not found in OAuth2 response")
        val name = user.attributes["name"] as? String
            ?: throw OAuth2AuthenticationException("Name not found in OAuth2 response")

        // Check if user is authorized in your database
        val embabelUser = userService.findByEmail(email) ?: userService.provisionUser(
            SimpleUser(
                username = name,
                displayName = name,
                email = email,
                id = java.util.UUID.randomUUID().toString(),
            )
        )

//        if (!authorizedUser.isActive) {
//            throw OAuth2AuthenticationException("User account is inactive: $email")
//        }

//        val authorities = authorizedUser.roles.map {
//            SimpleGrantedAuthority("ROLE_${it.name}")
//        }

        val authorities = listOf(SimpleGrantedAuthority("ROLE_USER"))

        return EmbabelAuth2User(
            embabelUser,
            user.attributes,
            authorities,
        )
    }
}

class EmbabelAuth2User(
    private val user: User,
    private val attributes: Map<String, Any>,
    private val authorities: Collection<GrantedAuthority>
) : OAuth2User {

    override fun getName(): String = user.displayName

    override fun getAttributes(): Map<String, Any> = attributes

    override fun getAuthorities(): Collection<GrantedAuthority> = authorities

    fun getUser(): User = user
}