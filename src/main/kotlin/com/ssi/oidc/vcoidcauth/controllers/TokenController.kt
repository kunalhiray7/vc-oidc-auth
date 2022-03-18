package com.ssi.oidc.vcoidcauth.controllers

import com.ssi.oidc.vcoidcauth.services.AuthService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["*"], methods = [RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.OPTIONS])
@RestController
class TokenController(private val service: AuthService) {

    @GetMapping("/api/v1/share_request_token/{tokenId}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getShareRequestToken(@PathVariable("tokenId") tokenId: String) = service.fetchCredShareRequestToken(tokenId)

    @GetMapping("/api/v1/token")
    fun getIdToken() {
        println("GET TOKEN CALLED...")
    }
}
