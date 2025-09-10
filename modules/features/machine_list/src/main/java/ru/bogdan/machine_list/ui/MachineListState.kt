package ru.bogdan.machine_list.ui

import domain.mechanic.*

data class MachineListState(
    val machines: List<Machine> = emptyList(),
    val isLoading: Boolean = false,
    val isFiltersShow: Boolean = false,
    val filterState: MachineListFilterState = MachineListFilterState()
)

data class MachineListFilterState(
    val stateFilter: List<MachineState> = emptyList(),
    val modeFilter: List<MachineModel> = emptyList(),
    val typeFilter: List<MachineType> = emptyList(),
    val yearFilter: IntRange = 1000..5000,
    val partFilter: List<PartOfMachine> = emptyList(),
    val oilFilter: List<Oil> = emptyList(),
    val stateDescriptions: List<String> = emptyList(),
    val typeDescriptions: List<String> = emptyList(),
    val minYear: Int = 100,
    val maxYear: Int = 4000,
)