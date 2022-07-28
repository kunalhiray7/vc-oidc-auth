package com.ssi.oidc.vcoidcauth.dtos

data class VerificationResponse(
    val idToken: String,
    val redirectUrl: String
)
