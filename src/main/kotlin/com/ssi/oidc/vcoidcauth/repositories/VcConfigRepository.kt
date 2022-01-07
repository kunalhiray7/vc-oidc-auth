package com.ssi.oidc.vcoidcauth.repositories

import com.ssi.oidc.vcoidcauth.domain.VcConfigEntity
import org.springframework.data.jpa.repository.JpaRepository

interface VcConfigRepository: JpaRepository<VcConfigEntity, Long>
