package com.ssi.oidc.vcoidcauth.controllers

import com.ssi.oidc.vcoidcauth.dtos.VerifyCredentialShareTokenRequest
import com.ssi.oidc.vcoidcauth.exceptions.CredShareRequestTokenNotFoundException
import com.ssi.oidc.vcoidcauth.services.AuthService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@SpringBootTest
class TokenControllerTest {

    private lateinit var mockMvc: MockMvc

    @Mock
    private lateinit var authService: AuthService

    @InjectMocks
    private lateinit var tokenController: TokenController

    @BeforeEach
    fun setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(tokenController).build()
    }

    @Test
    fun `GET share request token should return the request token for given token ID`() {
        val tokenId = "jknfjkdlsnkf"
        val token = "dnfjkasdnfl"
        doReturn(token).`when`(authService).fetchCredShareRequestToken(tokenId)

        mockMvc.get("/api/v1/share_request_token/$tokenId")
            .andExpect {
                status { isOk() }
                content { string(token) }
            }
        verify(authService, times(1)).fetchCredShareRequestToken(tokenId)
    }

    @Test
    fun `GET share request token should return NOT_FOUND when no token found for given token ID`() {
        val tokenId = "jknfjkdlsnkf"
        doThrow(CredShareRequestTokenNotFoundException("not found")).`when`(authService).fetchCredShareRequestToken(tokenId)

        mockMvc.get("/api/v1/share_request_token/$tokenId")
            .andExpect {
                status { isNotFound() }
            }
        verify(authService, times(1)).fetchCredShareRequestToken(tokenId)
    }

    @Test
    fun `POST verify share tokens should verify the credential share request and response tokens from holder`() {
        val request = VerifyCredentialShareTokenRequest(
            credentialShareRequestToken = "sjadfnjasdnfkals",
            credentialShareResponseToken = "flsdanflkdsnfk"
        )

    }
}
