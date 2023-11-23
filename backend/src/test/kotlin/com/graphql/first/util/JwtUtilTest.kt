package com.graphql.first.util

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class JwtUtilTest {


    @Test
    fun generateJwtTokenAndVerify() {

        val signedSecret = "lalallaallalaalalalalalaaaalalalalalalalallalalalala"

        val subject = "Robert"

        val token = JwtUtil.generateJwtToken(
            username = subject,
            signedSecret = signedSecret,
            roles = listOf(
                "admin",
                "user"
            )
        )

        assertNotNull(token)

        val claims = JwtUtil.validateJwtToken(
            token, signedSecret
        )

        assertEquals(claims.subject, subject)
    }
}