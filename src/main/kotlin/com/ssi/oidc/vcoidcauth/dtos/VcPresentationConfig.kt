package com.ssi.oidc.vcoidcauth.dtos

import com.fasterxml.jackson.annotation.JsonProperty
import com.ssi.oidc.vcoidcauth.domain.VcConfigEntity

data class VcPresentationConfig(
        @JsonProperty("id")
        val id: Long = 0,

        @JsonProperty("subject_identifier")
        val subjectIdentifier: String = "",

        @JsonProperty("name")
        val name: String = "",

        @JsonProperty("requested_attributes")
        val requestedAttributes: Set<Attribute> = emptySet()
) {
    fun toEntity(): VcConfigEntity = VcConfigEntity(
            subjectIdentifier = subjectIdentifier,
            name = name,
            requestedAttributes = requestedAttributes.map { it.toAttributeEntity() }.toSet()
    )
}

data class Attribute(
        @JsonProperty("name")
        val name: String = "",

        @JsonProperty("restrictions")
        val restrictions: Restriction
) {
    fun toAttributeEntity(): com.ssi.oidc.vcoidcauth.domain.Attribute = com.ssi.oidc.vcoidcauth.domain.Attribute(
            name = name,
            restrictions = restrictions.toRestrictionsEntity()
    )
}

data class Restriction(
        @JsonProperty("schema_id")
        val schemaId: String,

        @JsonProperty("schema_issuer_did")
        val schemaIssuerDid: String,

        @JsonProperty("schema_name")
        val schemaName: String,

        @JsonProperty("schema_version")
        val schemaVersion: String,

        @JsonProperty("issuer_did")
        val issuerDid: String,

        @JsonProperty("cred_def_id")
        val credDefId: String
) {
    fun toRestrictionsEntity(): com.ssi.oidc.vcoidcauth.domain.Restriction = com.ssi.oidc.vcoidcauth.domain.Restriction(
            schemaId = schemaId,
            schemaIssuerDid = schemaIssuerDid,
            schemaName = schemaName,
            schemaVersion = schemaVersion,
            issuerDid = issuerDid,
            credDefId = credDefId
    )
}
