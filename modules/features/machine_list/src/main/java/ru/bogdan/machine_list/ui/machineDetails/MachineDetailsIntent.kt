package ru.bogdan.machine_list.ui.machineDetails

import domain.mechanic.MachineDocument

sealed interface MachineDetailsIntent {
    data class ShowDialog(val doc: MachineDocument) : MachineDetailsIntent
    data object HideDialog : MachineDetailsIntent
    data class ExpandChange(val value: Boolean) : MachineDetailsIntent
    data object HideStates : MachineDetailsIntent
    data class SelectState(val state: String) : MachineDetailsIntent
    data object SaveDoc : MachineDetailsIntent
}