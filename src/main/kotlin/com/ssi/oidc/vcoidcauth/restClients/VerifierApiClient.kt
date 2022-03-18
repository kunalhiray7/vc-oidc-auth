package com.ssi.oidc.vcoidcauth.restClients

import com.ssi.oidc.vcoidcauth.dtos.CredShareResponseTokenRequest
import com.ssi.oidc.vcoidcauth.dtos.CredShareResponseTokenResponse
import com.ssi.oidc.vcoidcauth.dtos.CredentialShareRequest
import com.ssi.oidc.vcoidcauth.dtos.CredentialShareResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@FeignClient(name = "verifierApi", url = "\${verifierApi.url}")
interface VerifierApiClient {
    @RequestMapping(method = [RequestMethod.POST], value = ["/verifier/build-credential-request"],
        headers = ["Api-Key=\${verifierApi.apiKey}"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun buildCredentialShareRequest(request: CredentialShareRequest): CredentialShareResponse

    @RequestMapping(method = [RequestMethod.POST], value = ["/verifier/verify-share-response"],
        headers = ["Api-Key=\${verifierApi.apiKey}"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun verifyShareResponseToken(@RequestHeader("Authorization") accessToken: String,
                                 @RequestBody shareResponseTokenRequest: CredShareResponseTokenRequest): CredShareResponseTokenResponse
}
