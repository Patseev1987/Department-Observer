package ru.bogdan.main_screen_feature.ui.homeScreen

import androidx.compose.ui.graphics.Color
import domain.info.Info
import domain.mechanic.Machine
import domain.mechanic.MachineState

data class HomeScreenState(
    val photo: String? = null,
    val name: String = "",
    val surname: String = "",
    val patronymic: String = "",
    val role: String = "",
    val isLoading: Boolean = true,
    val error: Throwable? = null,
    val infoAboutMachines: List<InfoAboutMachines> = listOf(),
    val machines: List<Machine> = emptyList(),
    val isShowRepairList: Boolean = false,
    val isShowInfoList: Boolean = false,
    val repairList: List<Machine> = emptyList(),
    val info: List<Info> = emptyList()
)

data class InfoAboutMachines(
    val state: MachineState = MachineState.REPAIR,
    val percentage: Float = 0F,
    val title: String = "",
    val count: Int = 0,
    val color: Color = Color.Red,
)