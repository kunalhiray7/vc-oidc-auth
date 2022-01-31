package com.ssi.oidc.vcoidcauth.controllers

import com.ssi.oidc.vcoidcauth.domain.Registration
import com.ssi.oidc.vcoidcauth.dtos.OidcAuthRequest
import com.ssi.oidc.vcoidcauth.exceptions.UnAuthorizedClientException
import com.ssi.oidc.vcoidcauth.repositories.RegistrationRepository
import com.ssi.oidc.vcoidcauth.services.AuthService
import com.ssi.oidc.vcoidcauth.services.VerifierService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class AuthServiceTest {

    @Mock
    private lateinit var verifierService: VerifierService

    @Mock
    private lateinit var registrationRepository: RegistrationRepository

    @InjectMocks
    private lateinit var service: AuthService

    @Test
    fun `authorize() should throw UnAuthorizedCleintException when no relying party client for given clientId and clientSecret`() {
        val request = OidcAuthRequest(
            clientId = "dummy client id",
            clientSecret = "dummy client secret",
            scope = "openid vc_authn",
            presReqConfId = "",
            responseType = "id_token",
            redirectUri = "http://something.com"
        )
        doReturn(null).`when`(registrationRepository).findByClientIdAndClientSecret(request.clientId, request.clientSecret)

        val exception = assertThrows<UnAuthorizedClientException> { service.authorize(request) }

        assertEquals("No client found for given clientId and clientSecret.", exception.message)
        verify(registrationRepository, times(1)).findByClientIdAndClientSecret(request.clientId, request.clientSecret)
    }

    @Test
    fun `authorize() should call verifier service to build the credential request`() {
        val request = OidcAuthRequest(
            clientId = "dummy client id",
            clientSecret = "dummy client secret",
            scope = "openid vc_authn",
            presReqConfId = "",
            responseType = "id_token",
            redirectUri = "http://something.com"
        )
        val registration = Registration(clientId = request.clientId, clientSecret = request.clientSecret)
        doReturn(registration).`when`(registrationRepository).findByClientIdAndClientSecret(request.clientId, request.clientSecret)

        service.authorize(request)

        verify(verifierService, times(1)).buildCredentialRequest()
    }
}
