package com.ssi.oidc.vcoidcauth.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class PresentationConfigNotFound(override val message: String?): Exception(message)
