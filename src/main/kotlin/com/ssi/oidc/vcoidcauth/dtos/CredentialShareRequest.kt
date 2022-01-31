package com.ssi.oidc.vcoidcauth.dtos

data class CredentialShareRequest(
    val requirements: List<CredentialRequirements> = emptyList(),
)

data class CredentialRequirements(
    val type: List<String> = emptyList()
)

data class CredentialShareResponse(
    val credentialShareRequest: Any = Any()
)
