package com.ssi.oidc.vcoidcauth.dtos

import com.fasterxml.jackson.annotation.JsonProperty
import com.ssi.oidc.vcoidcauth.domain.Registration

data class ClientRegistrationResponse(
    @JsonProperty("client_id")
    val clientId: String,

    @JsonProperty("client_secret")
    val clientSecret: String
) {
    companion object {
        fun fromDomain(domain: Registration) = ClientRegistrationResponse(
            clientId = domain.clientId,
            clientSecret = domain.clientSecret
        )
    }
}
