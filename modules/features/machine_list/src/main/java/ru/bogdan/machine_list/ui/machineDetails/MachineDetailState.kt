package ru.bogdan.machine_list.ui.machineDetails

import androidx.compose.ui.graphics.Color
import domain.mechanic.MachineDocument
import domain.mechanic.Oil
import domain.mechanic.PartOfMachine
import ru.bogdan.core_ui.ui.theme.Emerald

data class MachineDetailState(
    val isLoading: Boolean = false,
    val name: String = "",
    val model: String = "",
    val imageUrl: String? = null,
    val description: String = "",
    val state: String = "",
    val states: List<String> = emptyList(),
    val expanded: Boolean = false,
    val type: String = "",
    val color: Color = Emerald,
    val yearOfManufacture: Int = 1000,
    val parts: Map<PartOfMachine, Int> = mapOf(),
    val docs: List<MachineDocument> = emptyList(),
    val oils: Map<Oil, Int> = mapOf(),
    val isShowDialog: Boolean = false,
    val docName: String = "",
)
