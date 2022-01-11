package com.ssi.oidc.vcoidcauth.repositories

import com.ssi.oidc.vcoidcauth.domain.Registration
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RegistrationRepository: JpaRepository<Registration, String>
