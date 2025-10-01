package ru.bogdan.core_impl.data.network.models.mechanic

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class OilWeb(
    val id: String,
    val name: String,
    val description: String = "",
    @SerialName("image_url")
    val imageUrl: String? = null,
) {
    companion object {
        val NONE = OilWeb(
            imageUrl = "https://avatars.mds.yandex.net/get-mpic/11853589/2a0000018c6e52063f45298a9cea075a7440/orig",
            id = "o1",
            name = "И-20А",
            description = "Масло веретенное И-20 А"
        )
    }
}
