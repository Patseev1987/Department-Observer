package domain.mechanic

import androidx.compose.runtime.Immutable

@Immutable
data class Oil(
    val id: String,
    val name: String,
    val description: String = "",
    val imageUrl: String? = null,
)
