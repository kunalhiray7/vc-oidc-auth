package com.ssi.oidc.vcoidcauth.dtos

import com.fasterxml.jackson.annotation.JsonProperty
import com.ssi.oidc.vcoidcauth.domain.Registration
import java.net.URI

data class ClientRegistrationRequest(
    @JsonProperty("client_name")
    val clientName: String,

    @JsonProperty("application_type")
    val applicationType: String,

    @JsonProperty("redirect_uris")
    val redirectUris: List<URI>
) {
    fun toDomain() = Registration(
        clientName = clientName,
        applicationType = applicationType,
        redirectUris = redirectUris.map { it.toString() }.reduce { acc, uri -> "$acc, $uri" }
    )
}
