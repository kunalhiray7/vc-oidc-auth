package com.ssi.oidc.vcoidcauth.domain

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Registration(
    @Id
    val clientId: String = UUID.randomUUID().toString(),

    val clientSecret: String = UUID.randomUUID().toString(),

    val clientName: String = "",

    val applicationType: String = "",

    val redirectUris: String = ""
)
