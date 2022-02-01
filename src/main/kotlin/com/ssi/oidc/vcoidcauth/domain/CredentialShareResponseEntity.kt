package com.ssi.oidc.vcoidcauth.domain

import java.time.ZonedDateTime
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Lob

@Entity
data class CredentialShareResponseEntity(

    @Id
    val id: String = UUID.randomUUID().toString(),

    @Lob
    @Column(length = 2500)
    val token: String = "",

    val timestamp: ZonedDateTime = ZonedDateTime.now()
)
