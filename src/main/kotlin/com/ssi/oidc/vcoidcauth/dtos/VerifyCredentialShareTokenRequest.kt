package com.ssi.oidc.vcoidcauth.dtos

data class VerifyCredentialShareTokenRequest(
    val credentialShareRequestToken: String = "",
    val credentialShareResponseToken: String = ""
)
