package domain.mechanic

import androidx.compose.runtime.Immutable

@Immutable
data class PartOfMachine(
    val id: String,
    val name: String,
    val description: String,
)
