package com.ssi.oidc.vcoidcauth.dtos

import com.fasterxml.jackson.annotation.JsonProperty

data class CredShareResponseTokenRequest(
    val credentialShareRequestToken: String? = null,
    val credentialShareResponseToken: String = "",
    val isHolderMustBeSubject: Boolean? = true
)

data class CredShareResponseTokenResponse(
    val jti: String = "",
    val errors: List<String> = emptyList(),
    val issuer: String = "",
    val isValid: Boolean = false,
    val suppliedCredentials: List<Credential> = emptyList()
)

data class Credential(
    @JsonProperty("@context")
    val context: Any = Any(),
    val id: String = "",
    val type: List<String> = emptyList(),
    val name: String = "",
    val holder: Holder = Holder(),
    val credentialSubject: CredentialSubject = CredentialSubject(),
    val issuanceDate: String = "",
    val credentialStatus: Any? = Any(),
    val issuer: String = "",
    val proof: Any = Any()
)

data class Holder(
    val id: String = ""
)

data class CredentialSubject(
    val data: CredentialData = CredentialData()
)

data class CredentialData(
    val type: List<String> = emptyList(),
    val name: String = "",
    val email: String = ""
)
