package com.ssi.oidc.vcoidcauth.controllers

import com.ssi.oidc.vcoidcauth.dtos.VcPresentationConfig
import com.ssi.oidc.vcoidcauth.services.VcConfigService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class VcConfigController(private val vcConfigService: VcConfigService) {

    @PostMapping("/vc-config")
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody vcConfig: VcPresentationConfig) = vcConfigService.create(vcConfig)

    @GetMapping("/vc-config")
    fun index() = vcConfigService.fetchAll()

    @GetMapping("/vc-config/{id}")
    fun findOne(@PathVariable("id") id: Long) = vcConfigService.findOne(id)
}
