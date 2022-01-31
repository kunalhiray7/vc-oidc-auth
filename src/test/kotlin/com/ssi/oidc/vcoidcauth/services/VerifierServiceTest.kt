package com.ssi.oidc.vcoidcauth.services

import com.ssi.oidc.vcoidcauth.Constants.Companion.EMAIL_CREDENTIAL_TYPE
import com.ssi.oidc.vcoidcauth.dtos.CredentialRequirements
import com.ssi.oidc.vcoidcauth.dtos.CredentialShareRequest
import com.ssi.oidc.vcoidcauth.dtos.LoginRequest
import com.ssi.oidc.vcoidcauth.dtos.LoginResponse
import com.ssi.oidc.vcoidcauth.restClients.CloudWalletApiClient
import com.ssi.oidc.vcoidcauth.restClients.VerifierApiClient
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class VerifierServiceTest {

    @Mock
    private lateinit var verifierApiClient: VerifierApiClient

    @Mock
    private lateinit var cloudWalletApiClient: CloudWalletApiClient

    private val verifierUsername = "dummy_verifier"

    private val verifierPassword = "dummy_password"

    private lateinit var service: VerifierService

    @BeforeEach
    fun setUp() {
        service = VerifierService(verifierApiClient, cloudWalletApiClient, verifierUsername, verifierPassword)
    }

    @Test
    fun `buildCredentialRequest() should call the verifier API to build the credential share request`() {
        val credentialShareRequest = CredentialShareRequest(
            requirements = listOf(
                CredentialRequirements(
                    type = listOf(
                        EMAIL_CREDENTIAL_TYPE
                    )
                )
            )
        )
        val accessToken = "dummy_access_token"
        val loginRequest = LoginRequest(verifierUsername, verifierPassword)
        doReturn(LoginResponse(accessToken, "did:elem:something")).`when`(cloudWalletApiClient).login(loginRequest)

        service.buildCredentialRequest()

        verify(cloudWalletApiClient, times(1)).login(loginRequest)
        verify(cloudWalletApiClient, times(1)).buildCredentialShareRequest(accessToken, credentialShareRequest)
    }
}
