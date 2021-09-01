package com.ssi.oidc.vcoidcauth

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class VcOidcAuthApplication

fun main(args: Array<String>) {
	runApplication<VcOidcAuthApplication>(*args)
}
