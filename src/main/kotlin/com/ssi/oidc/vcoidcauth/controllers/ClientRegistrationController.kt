package com.ssi.oidc.vcoidcauth.controllers

import com.ssi.oidc.vcoidcauth.dtos.ClientRegistrationRequest
import com.ssi.oidc.vcoidcauth.services.RegistrationService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
class ClientRegistrationController(private val service: RegistrationService) {

    @PostMapping("/api/v1/register")
    @ResponseStatus(HttpStatus.CREATED)
    fun register(@RequestBody request: ClientRegistrationRequest) = service.register(request)

    @GetMapping("/api/v1/registration/{clientId}")
    fun getOne(@PathVariable("clientId") clientId: String) = service.findOneFor(clientId)
}
