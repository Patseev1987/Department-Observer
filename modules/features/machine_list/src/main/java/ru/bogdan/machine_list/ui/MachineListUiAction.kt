package ru.bogdan.machine_list.ui

sealed interface MachineListUiAction {
    data object GoToLoginScreen : MachineListUiAction
}