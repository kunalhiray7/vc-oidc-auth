package com.ssi.oidc.vcoidcauth.dtos

data class CredentialShareRequest(
    val requirements: List<CredentialRequirements> = emptyList(),
    val callbackUrl: String? = null
)

data class CredentialRequirements(
    val type: List<String> = emptyList()
)

data class CredentialShareResponse(
    val credentialShareRequest: Any = Any()
)
