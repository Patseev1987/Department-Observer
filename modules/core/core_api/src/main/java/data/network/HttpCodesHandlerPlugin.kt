package data.network

import io.ktor.client.call.body
import io.ktor.client.plugins.api.createClientPlugin

val HttpCodesHandlerPlugin = createClientPlugin("HttpCodesHandlerPlugin"){
    onResponse {
        when (it.status.value) {
            in 200..299 -> {
                return@onResponse
            }

            401 -> {
                throw RuntimeException("Unauthorized")
            }

            403 -> {
                throw RuntimeException("Forbidden")
            }

            in 500..599 -> {
                throw RuntimeException("Server error")
            }

            else -> {
                throw RuntimeException("Bad request")
            }
        }
    }
}