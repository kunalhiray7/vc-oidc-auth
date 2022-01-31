package com.ssi.oidc.vcoidcauth.dtos

data class OidcAuthRequest(
    val clientId: String,

    val clientSecret: String,

    val scope: String,

    val presReqConfId: String,

    val responseType: String,

    val redirectUri: String
)
