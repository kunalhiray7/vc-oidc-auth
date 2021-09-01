package com.ssi.oidc.vcoidcauth.utils

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule

object ObjectMapperUtil {

    fun getObjectMapper(): ObjectMapper {
        val objectMapper = ObjectMapper()

        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)

        objectMapper.registerModule(KotlinModule())

        return objectMapper
    }

}
