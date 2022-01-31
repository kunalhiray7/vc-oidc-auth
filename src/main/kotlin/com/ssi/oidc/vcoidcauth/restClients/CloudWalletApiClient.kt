package com.ssi.oidc.vcoidcauth.restClients

import com.ssi.oidc.vcoidcauth.dtos.CredentialShareRequest
import com.ssi.oidc.vcoidcauth.dtos.LoginRequest
import com.ssi.oidc.vcoidcauth.dtos.LoginResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@FeignClient(name = "cloudWalletApi", url = "\${cloudWalletApi.url}")
interface CloudWalletApiClient {

    @RequestMapping(
        method = [RequestMethod.POST], value = ["/users/login"],
        headers = ["Api-Key=\${cloudWalletApi.apiKey}"], consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun login(request: LoginRequest): LoginResponse

    @RequestMapping(
        method = [RequestMethod.POST], value = ["/wallet/credential-share-token/generate-request-token"],
        headers = ["Api-Key=\${cloudWalletApi.apiKey}"], consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun buildCredentialShareRequest(
        @RequestHeader("Authorization") accessToken: String,
        @RequestBody request: CredentialShareRequest
    ): String
}
