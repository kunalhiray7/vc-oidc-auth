package com.ssi.oidc.vcoidcauth.dtos

import com.ssi.oidc.vcoidcauth.domain.VcConfigEntity

data class VcPresentationConfigResponse(
        val id: Long? = null
) {
    companion object {
        fun fromDomain(domain: VcConfigEntity): VcPresentationConfigResponse {
            return VcPresentationConfigResponse(id = domain.id)
        }
    }
}
