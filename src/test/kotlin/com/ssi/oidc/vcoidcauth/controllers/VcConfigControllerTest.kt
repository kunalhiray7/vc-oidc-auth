package com.ssi.oidc.vcoidcauth.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.ssi.oidc.vcoidcauth.dtos.VcPresentationConfigRequest
import com.ssi.oidc.vcoidcauth.services.VcConfigService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@SpringBootTest
class VcConfigControllerTest {

    private lateinit var mockMvc: MockMvc

    private val mapper = ObjectMapper()

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
        val request = VcPresentationConfigRequest()

        mockMvc.perform(post("/api/v1/vc-config")
                .content(mapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated)

        verify(vcConfigService, times(1)).create(request)
    }

    @Test
    fun `GET should return all the configurations for the VC Auth`() {

        mockMvc.perform(get("/api/v1/vc-config")).andExpect(status().isOk)

        verify(vcConfigService, times(1)).fetchAll()
    }
}
