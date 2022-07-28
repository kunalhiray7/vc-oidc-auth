package com.ssi.oidc.vcoidcauth.controllers

import com.ssi.oidc.vcoidcauth.dtos.CredShareResponseTokenRequest
import com.ssi.oidc.vcoidcauth.dtos.VerificationResponse
import com.ssi.oidc.vcoidcauth.services.AuthService
import com.ssi.oidc.vcoidcauth.services.TokenService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["*"], methods = [RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.OPTIONS])
@RestController
class TokenController(private val service: AuthService, private val tokenService: TokenService) {

    @GetMapping("/api/v1/share_request_token/{tokenId}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getShareRequestToken(@PathVariable("tokenId") tokenId: String) = service.fetchCredShareRequestToken(tokenId)

    @GetMapping("/api/v1/token")
    fun getIdToken() {
        println("GET TOKEN CALLED...")
    }

    @PostMapping("/api/v1/verify_share_response_token")
    fun verifyShareResponse(
        @RequestBody shareResponseTokenRequest: CredShareResponseTokenRequest
    ): VerificationResponse {
        return tokenService.generateFromCredential(service.verifyShareResponse(shareResponseTokenRequest))
    }
}
