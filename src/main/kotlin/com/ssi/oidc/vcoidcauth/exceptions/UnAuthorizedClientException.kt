package com.ssi.oidc.vcoidcauth.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.UNAUTHORIZED)
class UnAuthorizedClientException(override val message: String?): Exception(message)
