package com.ssi.oidc.vcoidcauth.services

import com.ssi.oidc.vcoidcauth.domain.VcConfigEntity
import com.ssi.oidc.vcoidcauth.dtos.VcPresentationConfig
import com.ssi.oidc.vcoidcauth.dtos.VcPresentationConfigResponse
import com.ssi.oidc.vcoidcauth.repositories.VcConfigRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
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
        doReturn(entity).`when`(repository).save(entity)

        val result = service.create(request)

        assertEquals(response, result)
        verify(repository, times(1)).save(entity)
    }

    @Test
    fun `fetchAll() should fetch all the available VC configurations`() {
        val vcConfigs = listOf(VcConfigEntity(id = 122L, subjectIdentifier = "email"),
            VcConfigEntity(id = 124L, subjectIdentifier = "email2"))
        doReturn(vcConfigs).`when`(repository).findAll()

        val result = service.fetchAll()

        assertEquals(vcConfigs, result)
        verify(repository, times(1)).findAll()
    }
}
