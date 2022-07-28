package com.ssi.oidc.vcoidcauth.services

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class TokenServiceTest {
    private val tokenService = TokenService()
    private val secret = "mySecret"

    @Test
    fun `should generate JWT token for given email and name`() {
        val email = "kunal@hire.com"
        val name = "Kunal Hire"

        val token = tokenService.generate(email, name, secret)
        println(token)

        assertFalse(token.isEmpty())
    }
}
