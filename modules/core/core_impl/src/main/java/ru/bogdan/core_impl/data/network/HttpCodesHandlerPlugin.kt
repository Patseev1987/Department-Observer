package ru.bogdan.core_impl.data.network

import io.ktor.client.plugins.api.*
import utils.exceptions.BadRequestException
import utils.exceptions.ForbiddenException
import utils.exceptions.ServerErrorException
import utils.exceptions.UnauthorizedException

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