package domain.mechanic

import androidx.compose.runtime.Immutable

@Immutable
data class PartOfMachine(
    val id: String,
    val name: String,
    val description: String,
) {
    companion object {
        val NONE = PartOfMachine(
            id = "p1",
            name = "Gear Z12",
            description = "Small gear for turning",
        )
    }
}
