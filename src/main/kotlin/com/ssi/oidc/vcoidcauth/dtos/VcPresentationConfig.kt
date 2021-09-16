package com.ssi.oidc.vcoidcauth.dtos

import com.fasterxml.jackson.annotation.JsonProperty

data class VcPresentationConfig(
        @JsonProperty("id")
        val id: String = "",

        @JsonProperty("subject_identifier")
        val subjectIdentifier: String = "",

        @JsonProperty("name")
        val name: String = "",

        @JsonProperty("requested_attributes")
        val requestedAttributes: List<Attribute> = emptyList()
)

data class Attribute(
        @JsonProperty("name")
        val name: String = "",

        @JsonProperty("restrictions")
        val restrictions: Restriction
)

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
)
