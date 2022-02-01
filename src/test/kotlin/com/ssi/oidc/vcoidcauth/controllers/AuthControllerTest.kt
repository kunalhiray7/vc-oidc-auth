package com.ssi.oidc.vcoidcauth.controllers

import com.ssi.oidc.vcoidcauth.dtos.OidcAuthRequest
import com.ssi.oidc.vcoidcauth.exceptions.UnAuthorizedClientException
import com.ssi.oidc.vcoidcauth.services.AuthService
import com.ssi.oidc.vcoidcauth.utils.ObjectMapperUtil.getObjectMapper
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
import org.springframework.util.LinkedMultiValueMap

@SpringBootTest
class AuthControllerTest {

    private lateinit var mockMvc: MockMvc
    private val mapper = getObjectMapper()

    @Mock
    private lateinit var authService: AuthService

    private lateinit var controller: AuthController

    private val selfBaseUrl = "http://localhost"

    @BeforeEach
    fun setUp() {
        controller = AuthController(authService, selfBaseUrl)
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build()
    }

    @Test
    fun `POST authorize should process the OIDC auth request`() {
        val request = OidcAuthRequest(
            clientId = "dummy client id",
            clientSecret = "dummy client secret",
            scope = "openid vc_authn",
            presReqConfId = "",
            responseType = "id_token",
            redirectUri = "http://something.com"
        )
        val requestParams = LinkedMultiValueMap<String, String>().apply {
            put("client_id", listOf(request.clientId))
            put("client_secret", listOf(request.clientSecret))
            put("scope", listOf(request.scope))
            put("pres_req_conf_id", listOf(request.presReqConfId))
            put("response_type", listOf(request.responseType))
            put("redirect_uri", listOf(request.redirectUri))
        }

        mockMvc.post("/api/v1/authorize") {
            contentType = MediaType.APPLICATION_FORM_URLENCODED
            params = requestParams
        }.andExpect {
            status { isFound() }
        }
        verify(authService, times(1)).authorize(request)
    }

    @Test
    fun `POST authorize should return 401 when relying party client not found`() {
        val request = OidcAuthRequest(
            clientId = "dummy client id",
            clientSecret = "dummy client secret",
            scope = "openid vc_authn",
            presReqConfId = "",
            responseType = "id_token",
            redirectUri = "http://something.com"
        )
        val requestParams = LinkedMultiValueMap<String, String>().apply {
            put("client_id", listOf(request.clientId))
            put("client_secret", listOf(request.clientSecret))
            put("scope", listOf(request.scope))
            put("pres_req_conf_id", listOf(request.presReqConfId))
            put("response_type", listOf(request.responseType))
            put("redirect_uri", listOf(request.redirectUri))
        }
        doThrow(UnAuthorizedClientException("Client not authorized")).`when`(authService).authorize(request)

        mockMvc.post("/api/v1/authorize") {
            contentType = MediaType.APPLICATION_FORM_URLENCODED
            params = requestParams
        }.andExpect {
            status { isUnauthorized() }
        }
        verify(authService, times(1)).authorize(request)
    }
}
