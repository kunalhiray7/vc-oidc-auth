package com.ssi.oidc.vcoidcauth.services

import com.ssi.oidc.vcoidcauth.domain.CredentialShareResponseEntity
import com.ssi.oidc.vcoidcauth.dtos.CredShareResponseTokenRequest
import com.ssi.oidc.vcoidcauth.dtos.OidcAuthRequest
import com.ssi.oidc.vcoidcauth.exceptions.CredShareRequestTokenNotFoundException
import com.ssi.oidc.vcoidcauth.exceptions.UnAuthorizedClientException
import com.ssi.oidc.vcoidcauth.repositories.CredentialShareTokenRepository
import com.ssi.oidc.vcoidcauth.repositories.RegistrationRepository
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val registrationRepository: RegistrationRepository,
    private val verifierService: VerifierService,
    private val credentialShareTokenRepository: CredentialShareTokenRepository
) {

    @Throws(UnAuthorizedClientException::class)
    fun authorize(request: OidcAuthRequest): String {
        registrationRepository.findByClientIdAndClientSecret(request.clientId, request.clientSecret)
            ?: throw UnAuthorizedClientException("No client found for given clientId and clientSecret.")
        val credentialRequestToken = verifierService.buildCredentialRequest()
        val savedToken = credentialShareTokenRepository.save(CredentialShareResponseEntity(token = credentialRequestToken))
        return savedToken.id
    }

    @Throws(CredShareRequestTokenNotFoundException::class)
    fun fetchCredShareRequestToken(tokenId: String): String {
        val credentialShareResponseEntity = credentialShareTokenRepository.findById(tokenId)
            .orElseThrow { CredShareRequestTokenNotFoundException("No token found for given ID") }
        return credentialShareResponseEntity.token
    }

    fun verifyShareResponse(shareResponseTokenRequest: CredShareResponseTokenRequest) =
        verifierService.verifyShareResponseToken(shareResponseTokenRequest)

}
