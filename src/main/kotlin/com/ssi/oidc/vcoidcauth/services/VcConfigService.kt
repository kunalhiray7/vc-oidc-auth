package com.ssi.oidc.vcoidcauth.services

import com.ssi.oidc.vcoidcauth.domain.VcConfigEntity
import com.ssi.oidc.vcoidcauth.dtos.VcPresentationConfig
import com.ssi.oidc.vcoidcauth.dtos.VcPresentationConfigResponse
import com.ssi.oidc.vcoidcauth.exceptions.PresentationConfigAlreadyExistsException
import com.ssi.oidc.vcoidcauth.exceptions.PresentationConfigNotFound
import com.ssi.oidc.vcoidcauth.repositories.VcConfigRepository
import org.springframework.stereotype.Service
import kotlin.jvm.Throws

@Service
class VcConfigService(val repository: VcConfigRepository) {

    @Throws(PresentationConfigAlreadyExistsException::class)
    fun create(request: VcPresentationConfig): VcPresentationConfigResponse {
        val savedEntity = repository.save(request.toEntity())
        return VcPresentationConfigResponse.fromDomain(savedEntity)
    }

    fun fetchAll(): MutableList<VcConfigEntity> = repository.findAll()

    @Throws(PresentationConfigNotFound::class)
    fun findOne(presentationConfigId: String): VcPresentationConfig {
        TODO("Not yet implemented")
    }

}
