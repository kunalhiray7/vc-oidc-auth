package com.ssi.oidc.vcoidcauth.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.Exception

@ResponseStatus(HttpStatus.NOT_FOUND)
class NoRegistrationFound(message: String): Exception(message)
