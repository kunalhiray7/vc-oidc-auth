package com.ssi.oidc.vcoidcauth.services

import com.ssi.oidc.vcoidcauth.Constants
import com.ssi.oidc.vcoidcauth.dtos.CredentialRequirements
import com.ssi.oidc.vcoidcauth.dtos.CredentialShareRequest
import com.ssi.oidc.vcoidcauth.dtos.LoginRequest
import com.ssi.oidc.vcoidcauth.restClients.CloudWalletApiClient
import com.ssi.oidc.vcoidcauth.restClients.VerifierApiClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class VerifierService(
    private val verifierApiClient: VerifierApiClient,
    private val cloudWalletApiClient: CloudWalletApiClient,
    @Value("\${verifier.username}")
    private val verifierUserName: String,
    @Value("\${verifier.password}")
    private val verifierPassword: String
) {

    fun buildCredentialRequest(): String {
        val credentialShareRequest = CredentialShareRequest(
            requirements = listOf(
                CredentialRequirements(
                    type = listOf(
                        Constants.EMAIL_CREDENTIAL_TYPE
                    )
                )
            )
        )
        val login = login()
        return cloudWalletApiClient.buildCredentialShareRequest(login.accessToken, credentialShareRequest)
    }

    private fun login() = cloudWalletApiClient.login(LoginRequest(verifierUserName, verifierPassword))

}
