package domain.mechanic

import androidx.compose.runtime.Immutable

@Immutable
data class Oil(
    val id: String,
    val name: String,
    val description: String = "",
    val imageUrl: String? = null,
) {
    companion object {
        val NONE = Oil(
            imageUrl = "https://avatars.mds.yandex.net/get-mpic/11853589/2a0000018c6e52063f45298a9cea075a7440/orig",
            id = "o1",
            name = "И-20А",
            description = "Масло веретенное И-20 А"
        )
    }
}
