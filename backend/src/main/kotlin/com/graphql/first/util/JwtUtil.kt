package com.graphql.first.util

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import java.nio.charset.Charset
import java.time.Instant
import java.util.*
import javax.crypto.SecretKey
import kotlin.collections.HashMap

object JwtUtil {

    fun generateJwtToken(
        username: String,
        signedSecret: String,
        roles: List<String> = listOf()
    ): String {
        val claims = HashMap<String, Any>()
        claims["roles"] = roles

        val currentDate = Date.from(Instant.now())

        val key = getSecretKey(signedSecret)

        return Jwts.builder()
            .claims().add(claims).and()
            .subject(username)
            .issuedAt(currentDate)
            .expiration(
                Date.from(
                    currentDate.toInstant().plusMillis(
                        1_000 * 60 * 60 * 24
                    )
                )
            )
            .id(UUID.randomUUID().toString())
            .signWith(key)
            .compact()
    }

    private fun getSecretKey(signedSecret: String): SecretKey =

        Keys.hmacShaKeyFor(signedSecret.toByteArray(Charsets.UTF_8))


    fun validateJwtToken(
        token: String,
        signedSecret: String
    ) =  Jwts.parser()
        .verifyWith(getSecretKey(signedSecret))
        .build()
        .parseSignedClaims(token)
        .payload

}