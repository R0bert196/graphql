package com.graphql.first.security

import com.graphql.first.util.JwtUtil
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component

@Component
class CustomAuthenticationProvider(

    @Value("\${secretKey}")
    private val secretKey: String
): AuthenticationProvider {


    override fun authenticate(authentication: Authentication?): Authentication {

        return runCatching {
            val customAuthentication = authentication as CustomAuthentication

            val claims = JwtUtil.validateJwtToken(customAuthentication.credentials, secretKey)

            CustomAuthentication(
                authenticated = true,
                username = claims.subject,
                roles = claims["roles"] as MutableCollection<String>
            )
        }.onFailure {
            throw BadCredentialsException("Wrong credentials")
        }.getOrThrow()
    }

    override fun supports(authentication: Class<*>?): Boolean {
        return CustomAuthentication::class.java == authentication
    }
}