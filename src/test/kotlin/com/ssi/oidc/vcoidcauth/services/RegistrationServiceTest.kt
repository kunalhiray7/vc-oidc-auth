package com.ssi.oidc.vcoidcauth.services

import com.ssi.oidc.vcoidcauth.domain.Registration
import com.ssi.oidc.vcoidcauth.dtos.ClientRegistrationRequest
import com.ssi.oidc.vcoidcauth.dtos.ClientRegistrationResponse
import com.ssi.oidc.vcoidcauth.exceptions.NoRegistrationFound
import com.ssi.oidc.vcoidcauth.repositories.RegistrationRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.net.URI
import java.util.*

@ExtendWith(MockitoExtension::class)
class RegistrationServiceTest {

    @Mock
    private lateinit var repository: RegistrationRepository

    @InjectMocks
    private lateinit var service: RegistrationService

    @Test
    fun `register() should call the repository to save a new client registration`() {
        val request = ClientRegistrationRequest(
            clientName = "New Client",
            applicationType = "web",
            redirectUris = listOf(URI("http://localhost:8080/my_app"), URI("http://localhost:8080/my_app/profile"))
        )
        val registration = request.toDomain()
        val response = ClientRegistrationResponse.fromDomain(registration)
        val argCaptor = ArgumentCaptor.forClass(Registration::class.java)
        doReturn(registration).`when`(repository).save(ArgumentMatchers.any())

        val result = service.register(request)

        assertEquals(response, result)
        verify(repository, times(1)).save(argCaptor.capture())
    }

    @Test
    fun `findOneFor() should return the client registration details for a given clientId`() {
        val clientId = "0c634bf6-de15-4112-b578-67ff5237a3c7"
        val registration = Registration(
            clientId = clientId,
            clientSecret = "43f12f71-f740-4c4a-a6ac-6d5f7c42a828",
            clientName = "New Client",
            applicationType = "Web",
            redirectUris = "http://something, http://somethingElse"
        )
        val response = ClientRegistrationResponse(
            clientId = clientId,
            clientSecret = registration.clientSecret
        )
        doReturn(Optional.of(registration)).`when`(repository).findById(clientId)

        val result = service.findOneFor(clientId)

        assertEquals(response, result)
        verify(repository, times(1)).findById(clientId)
    }

    @Test
    fun `findOneFor() should throw NoRegistrationFound Exception when no registration found for given clientID`() {
        val clientId = "0c634bf6-de15-4112-b578-67ff5237a3c7"
        doReturn(Optional.ofNullable(null)).`when`(repository).findById(clientId)

        val exception = assertThrows<NoRegistrationFound> { service.findOneFor(clientId) }

        assertEquals("No registration found for given client ID: $clientId", exception.message)
        verify(repository, times(1)).findById(clientId)
    }
}
