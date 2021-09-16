package com.ssi.oidc.vcoidcauth.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.Exception

@ResponseStatus(HttpStatus.CONFLICT)
class PresentationConfigAlreadyExistsException(override val message: String?): Exception(message)
