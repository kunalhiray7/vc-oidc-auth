package com.ssi.oidc.vcoidcauth.services

import com.ssi.oidc.vcoidcauth.dtos.OidcAuthRequest
import com.ssi.oidc.vcoidcauth.exceptions.UnAuthorizedClientException
import com.ssi.oidc.vcoidcauth.repositories.RegistrationRepository
import org.springframework.stereotype.Service

@Service
class AuthService(private val registrationRepository: RegistrationRepository,
private val verifierService: VerifierService) {

    @Throws(UnAuthorizedClientException::class)
    fun authorize(request: OidcAuthRequest): String {
        registrationRepository.findByClientIdAndClientSecret(request.clientId, request.clientSecret)
            ?: throw UnAuthorizedClientException("No client found for given clientId and clientSecret.")
        return verifierService.buildCredentialRequest()
    }

}
