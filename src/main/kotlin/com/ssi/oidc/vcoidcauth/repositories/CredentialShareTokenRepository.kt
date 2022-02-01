package com.ssi.oidc.vcoidcauth.repositories

import com.ssi.oidc.vcoidcauth.domain.CredentialShareResponseEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CredentialShareTokenRepository: JpaRepository<CredentialShareResponseEntity, String>
