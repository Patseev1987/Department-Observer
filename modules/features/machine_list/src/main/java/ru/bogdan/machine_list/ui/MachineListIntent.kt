package ru.bogdan.machine_list.ui

import domain.mechanic.MachineModel
import domain.mechanic.MachineState
import domain.mechanic.MachineType

sealed interface MachineListIntent {
    data class SetMachineStateFilter(val state: MachineState) : MachineListIntent
    data class SetMachineModelFilter(val model: MachineModel) : MachineListIntent
    data class SetMachineTypeFilter(val type: MachineType) : MachineListIntent
    data class SetMachineYearFilter(val years: IntRange) : MachineListIntent
    data object ChangeFilterShowState : MachineListIntent
    data object DropFilters : MachineListIntent
    data object ApplyFilters : MachineListIntent
    data object RefreshData : MachineListIntent
}
