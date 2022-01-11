package com.ssi.oidc.vcoidcauth.controllers

import com.ssi.oidc.vcoidcauth.dtos.ClientRegistrationRequest
import com.ssi.oidc.vcoidcauth.dtos.ClientRegistrationResponse
import com.ssi.oidc.vcoidcauth.services.RegistrationService
import com.ssi.oidc.vcoidcauth.utils.ObjectMapperUtil
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.net.URI

@SpringBootTest
class ClientRegistrationControllerTest {

    private lateinit var mockMvc: MockMvc

    private val mapper = ObjectMapperUtil.getObjectMapper()

    @Mock
    private lateinit var service: RegistrationService

    @InjectMocks
    private lateinit var controller: ClientRegistrationController

    @BeforeEach
    fun setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build()
    }

    @Test
    fun `POST should register a new client`() {
        val request = ClientRegistrationRequest(
            clientName = "New Client",
            applicationType = "web",
            redirectUris = listOf(URI("http://localhost:8080/my_app"), URI("http://localhost:8080/my_app/profile"))
        )
        val response = ClientRegistrationResponse(
            clientId = "34678tyuiovb",
            clientSecret = "45678yuiogbhjmkbn"
        )
        doReturn(response).`when`(service).register(request)

        mockMvc.post("/api/v1/register", request) {
            contentType = MediaType.APPLICATION_JSON
            content = mapper.writeValueAsString(request)
        }.andExpect {
            status { isCreated() }
            content { contentType(MediaType.APPLICATION_JSON) }
            content { json(mapper.writeValueAsString(response)) }
        }
        verify(service, times(1)).register(request)
    }

    @Test
    fun `GET should return registration details for a given client ID`() {
        val clientId = "0c634bf6-de15-4112-b578-67ff5237a3c7"
        val response = ClientRegistrationResponse(
            clientId = clientId,
            clientSecret = "43f12f71-f740-4c4a-a6ac-6d5f7c42a828"
        )
        doReturn(response).`when`(service).findOneFor(clientId)

        mockMvc.get("/api/v1/registration/$clientId").andExpect {
            status { isOk() }
            content { json(mapper.writeValueAsString(response)) }
        }
    }
}
