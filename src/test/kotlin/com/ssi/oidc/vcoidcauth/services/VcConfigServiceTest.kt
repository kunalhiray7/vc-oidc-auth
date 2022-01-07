package com.ssi.oidc.vcoidcauth.services

import com.ssi.oidc.vcoidcauth.dtos.VcPresentationConfig
import com.ssi.oidc.vcoidcauth.dtos.VcPresentationConfigResponse
import com.ssi.oidc.vcoidcauth.repositories.VcConfigRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class VcConfigServiceTest {

    @Mock
    private lateinit var repository: VcConfigRepository

    @InjectMocks
    private lateinit var service: VcConfigService

    @Test
    fun `create() should create the VC presentation configuration`() {
        val request = VcPresentationConfig(id = "124", subjectIdentifier = "email")
        val entity = request.toEntity()
        val response = VcPresentationConfigResponse.fromDomain(entity)
        Mockito.doReturn(entity).`when`(repository).save(entity)

        val result = service.create(request)

        assertEquals(response, result)
        Mockito.verify(repository, Mockito.times(1)).save(entity)
    }
}
