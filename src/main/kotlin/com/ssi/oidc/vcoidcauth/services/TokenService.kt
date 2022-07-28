package com.ssi.oidc.vcoidcauth.services

import com.ssi.oidc.vcoidcauth.dtos.CredShareResponseTokenResponse
import com.ssi.oidc.vcoidcauth.dtos.VerificationResponse
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Service
import org.springframework.web.servlet.view.RedirectView
import java.nio.charset.StandardCharsets

@Service
class TokenService {
    fun generate(email: String, name: String, secret: String): String {
//        val key = Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))
        val key = Keys.secretKeyFor(SignatureAlgorithm.HS256)

        return Jwts.builder().claim("email", email).claim("name", name).signWith(key).compact()
    }

    fun generateFromCredential(verifyShareResponse: CredShareResponseTokenResponse): VerificationResponse {
        val data = verifyShareResponse.suppliedCredentials[0].credentialSubject.data
        val token = generate(data.email, data.name, "someSecret")
        return VerificationResponse(token, "http://localhost:4000/home")
    }
}
