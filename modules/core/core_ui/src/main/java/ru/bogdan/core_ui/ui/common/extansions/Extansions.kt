package ru.bogdan.core_ui.ui.common.extansions

import androidx.compose.ui.graphics.Color
import domain.mechanic.MachineState
import ru.bogdan.core_ui.ui.theme.LightRed
import ru.bogdan.core_ui.ui.theme.Tiffany

fun MachineState.getColor(): Color = when (this) {
    MachineState.WORKING -> Tiffany
    MachineState.STOPPED -> Color.Gray
    MachineState.REPAIR -> LightRed
}