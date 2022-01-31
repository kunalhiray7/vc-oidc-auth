package com.ssi.oidc.vcoidcauth.dtos

data class LoginRequest(
        val username: String,
        val password: String
)

data class LoginResponse(
        val accessToken: String,
        val did: String
)
