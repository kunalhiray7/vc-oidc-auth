package com.ssi.oidc.vcoidcauth.services

import com.ssi.oidc.vcoidcauth.dtos.VcPresentationConfig
import com.ssi.oidc.vcoidcauth.exceptions.PresentationConfigAlreadyExistsException
import com.ssi.oidc.vcoidcauth.exceptions.PresentationConfigNotFound
import org.springframework.stereotype.Service
import kotlin.jvm.Throws

@Service
class VcConfigService {

    @Throws(PresentationConfigAlreadyExistsException::class)
    fun create(request: VcPresentationConfig) {
        TODO("Not yet implemented")
    }

    fun fetchAll(): List<VcPresentationConfig> {
        TODO("Not yet implemented")
    }

    @Throws(PresentationConfigNotFound::class)
    fun findOne(presentationConfigId: String): VcPresentationConfig {
        TODO("Not yet implemented")
    }

}
