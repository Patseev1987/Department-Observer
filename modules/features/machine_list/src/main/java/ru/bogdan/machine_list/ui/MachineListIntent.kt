package ru.bogdan.machine_list.ui

import domain.mechanic.MachineModel
import domain.mechanic.MachineState
import domain.mechanic.MachineType
import domain.mechanic.Oil
import domain.mechanic.PartOfMachine

sealed interface MachineListIntent {
    data class SetMachineStateFilter(val state: MachineState): MachineListIntent
    data class SetMachineModelFilter(val model: MachineModel): MachineListIntent
    data class SetMachineTypeFilter(val type: MachineType): MachineListIntent
    data class SetMachineYearFilter(val years: IntRange): MachineListIntent
    data class SetMachinePartsFilter(val part: PartOfMachine): MachineListIntent
    data class SetMachineOilsFilter(val oil: Oil): MachineListIntent
    data object ChangeFilterShowState : MachineListIntent
    data object DropFilters : MachineListIntent
    data object ApplyFilters : MachineListIntent
    data object RefreshData : MachineListIntent
}
