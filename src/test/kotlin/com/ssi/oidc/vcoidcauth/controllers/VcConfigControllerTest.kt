package com.ssi.oidc.vcoidcauth.controllers

import com.ssi.oidc.vcoidcauth.dtos.VcPresentationConfig
import com.ssi.oidc.vcoidcauth.exceptions.PresentationConfigAlreadyExistsException
import com.ssi.oidc.vcoidcauth.exceptions.PresentationConfigNotFound
import com.ssi.oidc.vcoidcauth.services.VcConfigService
import com.ssi.oidc.vcoidcauth.utils.ObjectMapperUtil.getObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@SpringBootTest
class VcConfigControllerTest {

    private lateinit var mockMvc: MockMvc

    private val mapper = getObjectMapper()

    @Mock
    private lateinit var vcConfigService: VcConfigService

    @InjectMocks
    private lateinit var vcConfigController: VcConfigController

    @BeforeEach
    fun setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(vcConfigController).build()
    }

    @Test
    fun `POST should add new configuration for the VC Auth`() {
        val request = VcPresentationConfig()

        mockMvc.perform(post("/api/v1/vc-config")
                .content(mapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated)

        verify(vcConfigService, times(1)).create(request)
    }

    @Test
    fun `POST should return 409 when tried to create the presentation config with an existing id`() {
        val request = VcPresentationConfig()
        doThrow(PresentationConfigAlreadyExistsException(message = "Config already exists")).`when`(vcConfigService).create(request)

        mockMvc.perform(post("/api/v1/vc-config")
                .content(mapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict)

        verify(vcConfigService, times(1)).create(request)
    }

    @Test
    fun `GET should return all the configurations for the VC Auth`() {
        val response = listOf(VcPresentationConfig(id = "123", name = "my_config"),
                VcPresentationConfig(id = "124", name = "my_config2"))
        doReturn(response).`when`(vcConfigService).fetchAll()

        mockMvc.perform(get("/api/v1/vc-config"))
                .andExpect(status().isOk)
                .andExpect(content().string(mapper.writeValueAsString(response)))

        verify(vcConfigService, times(1)).fetchAll()
    }

    @Test
    fun `GET should return the presentation config for a given ID`() {
        val presentationConfigId = "1234abcd"
        val response = VcPresentationConfig(id = presentationConfigId, name = "my_config")
        doReturn(response).`when`(vcConfigService).findOne(presentationConfigId)

        mockMvc.perform(get("/api/v1/vc-config/$presentationConfigId"))
                .andExpect(status().isOk)
                .andExpect(content().string(mapper.writeValueAsString(response)))

        verify(vcConfigService, times(1)).findOne(presentationConfigId)
    }

    @Test
    fun `GET should return 404 when presentation config with given ID does not exist`() {
        val presentationConfigId = "1234abcd"
        doThrow(PresentationConfigNotFound(message = "Config not found")).`when`(vcConfigService).findOne(presentationConfigId)

        mockMvc.perform(get("/api/v1/vc-config/$presentationConfigId"))
                .andExpect(status().isNotFound)

        verify(vcConfigService, times(1)).findOne(presentationConfigId)
    }
}
