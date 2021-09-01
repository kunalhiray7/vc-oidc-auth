package com.ssi.oidc.vcoidcauth.controllers

import com.ssi.oidc.vcoidcauth.dtos.VcPresentationConfigRequest
import com.ssi.oidc.vcoidcauth.services.VcConfigService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class VcConfigController(private val vcConfigService: VcConfigService) {

    @PostMapping("/vc-config")
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody vcConfigRequest: VcPresentationConfigRequest) = vcConfigService.create(vcConfigRequest)

    @GetMapping("/vc-config")
    fun index() = vcConfigService.fetchAll()
}
