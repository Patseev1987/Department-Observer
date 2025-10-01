package ru.bogdan.core_impl.data.network

import io.ktor.client.plugins.api.*

val HttpCodesHandlerPlugin = createClientPlugin("HttpCodesHandlerPlugin") {
    onResponse {
        when (it.status.value) {
            in 200..399 -> {
                return@onResponse
            }

            401 -> {
                throw UnauthorizedException()
            }

            403 -> {
                throw ForbiddenException()
            }

            in 500..599 -> {
                throw ServerErrorException()
            }

            else -> {
                throw BadRequestException()
            }
        }
    }
}