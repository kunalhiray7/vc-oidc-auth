package com.ssi.oidc.vcoidcauth.controllers

import com.ssi.oidc.vcoidcauth.domain.CredentialShareResponseEntity
import com.ssi.oidc.vcoidcauth.domain.Registration
import com.ssi.oidc.vcoidcauth.dtos.OidcAuthRequest
import com.ssi.oidc.vcoidcauth.exceptions.CredShareRequestTokenNotFoundException
import com.ssi.oidc.vcoidcauth.exceptions.UnAuthorizedClientException
import com.ssi.oidc.vcoidcauth.repositories.CredentialShareTokenRepository
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
import java.util.*

@ExtendWith(MockitoExtension::class)
class AuthServiceTest {

    @Mock
    private lateinit var verifierService: VerifierService

    @Mock
    private lateinit var credentialShareTokenRepository: CredentialShareTokenRepository

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
        val token = "eryughjbnmfghjkl"
        val registration = Registration(clientId = request.clientId, clientSecret = request.clientSecret)
        val credentialShareResponseEntity = CredentialShareResponseEntity(token = token)
        doReturn(registration).`when`(registrationRepository).findByClientIdAndClientSecret(request.clientId, request.clientSecret)
        doReturn(token).`when`(verifierService).buildCredentialRequest()
        doReturn(credentialShareResponseEntity).`when`(credentialShareTokenRepository).save(any())

        service.authorize(request)

        verify(verifierService, times(1)).buildCredentialRequest()
    }

    @Test
    fun `authorize() should store the credential share response`() {
        val request = OidcAuthRequest(
            clientId = "dummy client id",
            clientSecret = "dummy client secret",
            scope = "openid vc_authn",
            presReqConfId = "",
            responseType = "id_token",
            redirectUri = "http://something.com"
        )
        val token = "eryughjbnmfghjkl"
        val registration = Registration(clientId = request.clientId, clientSecret = request.clientSecret)
        val credentialShareResponseEntity = CredentialShareResponseEntity(token = token)
        doReturn(registration).`when`(registrationRepository).findByClientIdAndClientSecret(request.clientId, request.clientSecret)
        doReturn(token).`when`(verifierService).buildCredentialRequest()
        doReturn(credentialShareResponseEntity).`when`(credentialShareTokenRepository).save(any())

        val tokenId = service.authorize(request)

        assertEquals(credentialShareResponseEntity.id, tokenId)
        verify(credentialShareTokenRepository, times(1)).save(any())
    }

    @Test
    fun `fetchCredShareRequestToken() should return the credential share request token for the given id`() {
        val tokenId = "dfajkdsbfjklabs"
        val expectedToken = "dnfjnasjkldfnakl"
        val credentialShareResponseEntity = CredentialShareResponseEntity(token = expectedToken)
        doReturn(Optional.of(credentialShareResponseEntity)).`when`(credentialShareTokenRepository).findById(tokenId)

        val result = service.fetchCredShareRequestToken(tokenId)

        assertEquals(expectedToken, result)
        verify(credentialShareTokenRepository, times(1)).findById(tokenId)
    }

    @Test
    fun `fetchCredShareRequestToken() should throw exception when no token found for the given id`() {
        val tokenId = "dfajkdsbfjklabs"
        doReturn(Optional.ofNullable(null)).`when`(credentialShareTokenRepository).findById(tokenId)

        val exception = assertThrows<CredShareRequestTokenNotFoundException> { service.fetchCredShareRequestToken(tokenId) }

        assertEquals("No token found for given ID", exception.message)
        verify(credentialShareTokenRepository, times(1)).findById(tokenId)
    }
}
