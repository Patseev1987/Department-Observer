package ru.bogdan.core_impl.data.network.models.info

import kotlinx.serialization.Serializable

@Serializable
data class InfoWeb(
    val id: String,
    val name: String,
    val info: String,
)

