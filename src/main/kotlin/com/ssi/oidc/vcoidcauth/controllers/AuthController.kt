package com.ssi.oidc.vcoidcauth.controllers

import com.ssi.oidc.vcoidcauth.dtos.OidcAuthRequest
import com.ssi.oidc.vcoidcauth.services.AuthService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RequestMethod.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import org.springframework.web.servlet.view.RedirectView

@CrossOrigin(origins = ["*"], methods = [POST, GET, PUT, OPTIONS])
@Controller
class AuthController(private val service: AuthService) {

    @PostMapping("/api/v1/authorize", consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    @ResponseStatus(HttpStatus.FOUND)
    fun authorize(@RequestBody request: MultiValueMap<String, String>,
                  redirectAttributes: RedirectAttributes): RedirectView {
        val oidcAuthRequest = OidcAuthRequest(
            clientId = request.getFirst("client_id") ?: "",
            clientSecret = request.getFirst("client_secret") ?: "",
            scope = request.getFirst("scope") ?: "",
            presReqConfId = request.getFirst("pres_req_conf_id") ?: "",
            responseType = request.getFirst("response_type") ?: "",
            redirectUri = request.getFirst("redirect_uri") ?: ""
        )
        val shareRequestToken = service.authorize(oidcAuthRequest)

        redirectAttributes.addAttribute("shareToken", shareRequestToken)
        return RedirectView("authorize")
    }

    @GetMapping("/api/v1/authorize")
    @ResponseStatus(HttpStatus.FOUND)
    fun greeting(
        @RequestParam(name = "shareToken", required = true) shareToken: String?,
        model: Model
    ): String? {
        model.addAttribute("shareToken", shareToken)
        return "greeting"
    }
}
