package com.ssi.oidc.vcoidcauth.controllers

import com.ssi.oidc.vcoidcauth.dtos.CredShareResponseTokenRequest
import com.ssi.oidc.vcoidcauth.dtos.OidcAuthRequest
import com.ssi.oidc.vcoidcauth.services.AuthService
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.ModelMap
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RequestMethod.*
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import org.springframework.web.servlet.view.RedirectView
import javax.servlet.http.HttpServletResponse

@CrossOrigin(origins = ["*"], methods = [POST, GET, PUT, OPTIONS], allowedHeaders = ["*"])
@Controller
class AuthController(
    private val service: AuthService,
    @Value("\${self.baseUrl}")
    private val selfBaseUrl: String
) {

    @PostMapping("/api/v1/authorize", consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    @ResponseStatus(HttpStatus.FOUND)
    fun authorize(
        @RequestBody request: MultiValueMap<String, String>,
        redirectAttributes: RedirectAttributes
    ): RedirectView {
        val oidcAuthRequest = OidcAuthRequest(
            clientId = request.getFirst("client_id") ?: "",
            clientSecret = request.getFirst("client_secret") ?: "",
            scope = request.getFirst("scope") ?: "",
            presReqConfId = request.getFirst("pres_req_conf_id") ?: "",
            responseType = request.getFirst("response_type") ?: "",
            redirectUri = request.getFirst("redirect_uri") ?: ""
        )
        val savedShareTokenId = service.authorize(oidcAuthRequest)

        redirectAttributes.addAttribute("shareTokenUrl", "$selfBaseUrl/api/v1/share_request_token/$savedShareTokenId")
        return RedirectView("authorize")
    }

    @GetMapping("/api/v1/authorize")
    @ResponseStatus(HttpStatus.FOUND)
    fun greeting(
        @RequestParam(name = "shareTokenUrl", required = true) shareTokenUrl: String?,
        model: Model
    ): String? {
        model.addAttribute("shareTokenUrl", shareTokenUrl)
        return "greeting"
    }

    @PostMapping("/api/v1/verify_share_response_token")
    @ResponseStatus(HttpStatus.FOUND)
    fun verifyShareResponse(
        @RequestBody shareResponseTokenRequest: CredShareResponseTokenRequest,
        redirectAttributes: RedirectAttributes
    ): RedirectView {
        service.verifyShareResponse(shareResponseTokenRequest)
        redirectAttributes.addAttribute("id_token", "jwtToken")
        return RedirectView("id_token")
    }

    @GetMapping("/api/v1/id_token")
    @ResponseStatus(HttpStatus.FOUND)
    fun getToken(
        @RequestParam(name = "idToken", required = true) idToken: String?,
//        redirectAttributes: RedirectAttributes,
        model: ModelMap,
    response: HttpServletResponse
    ): String {
        val redirectUrl = "https://www.google.com"
//        redirectAttributes.addAttribute("id_token", idToken)
        model.addAttribute("id_token", idToken)
        model.addAttribute("redirectUrl", redirectUrl)
        return "redirectView"

//        response.status = 302
//        response.setHeader("Location", redirectUrl)

//        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(externalUrl)).build()
    }
}
