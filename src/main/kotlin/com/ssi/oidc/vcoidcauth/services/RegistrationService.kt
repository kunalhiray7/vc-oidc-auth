package com.ssi.oidc.vcoidcauth.services

import com.ssi.oidc.vcoidcauth.dtos.ClientRegistrationRequest
import com.ssi.oidc.vcoidcauth.dtos.ClientRegistrationResponse
import com.ssi.oidc.vcoidcauth.exceptions.NoRegistrationFound
import com.ssi.oidc.vcoidcauth.repositories.RegistrationRepository
import org.springframework.stereotype.Service

@Service
class RegistrationService(private val repository: RegistrationRepository) {

    fun register(request: ClientRegistrationRequest): ClientRegistrationResponse {
        val savedEntity = repository.save(request.toDomain())
        return ClientRegistrationResponse.fromDomain(savedEntity)
    }

    fun findOneFor(clientId: String): ClientRegistrationResponse =
        ClientRegistrationResponse.fromDomain(repository.findById(clientId)
                .orElseThrow { NoRegistrationFound("No registration found for given client ID: $clientId") })

}
