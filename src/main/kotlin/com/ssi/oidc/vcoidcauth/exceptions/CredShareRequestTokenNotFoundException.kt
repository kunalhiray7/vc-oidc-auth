package com.ssi.oidc.vcoidcauth.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class CredShareRequestTokenNotFoundException(message: String): Exception(message)
