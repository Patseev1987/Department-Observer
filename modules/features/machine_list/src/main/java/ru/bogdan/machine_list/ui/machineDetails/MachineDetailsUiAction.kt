package ru.bogdan.machine_list.ui.machineDetails

sealed interface MachineDetailsUiAction {
    data class ShowToast(val message: String) : MachineDetailsUiAction
}