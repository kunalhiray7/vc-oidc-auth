package com.ssi.oidc.vcoidcauth.domain

import javax.persistence.*

@Entity
data class VcConfigEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,

        val subjectIdentifier: String = "",

        val name: String = "",

        @OneToMany(mappedBy = "vcConfigEntity", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
        val requestedAttributes: Set<Attribute>? = null
)

@Entity
data class Attribute(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,

        val name: String = "",

        @OneToOne
        val restrictions: Restriction? = null,

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "config_id", nullable = false)
        val vcConfigEntity: VcConfigEntity? = VcConfigEntity()
)

@Entity
data class Restriction(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,

        val schemaId: String = "",

        val schemaIssuerDid: String = "",

        val schemaName: String = "",

        val schemaVersion: String = "",

        val issuerDid: String = "",

        val credDefId: String = ""
)
