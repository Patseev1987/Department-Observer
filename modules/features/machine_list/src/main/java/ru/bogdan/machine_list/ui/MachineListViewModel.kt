package ru.bogdan.machine_list.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.DataStoreManager
import data.NetworkRepository
import data.ResourceManager
import domain.mechanic.Machine
import domain.mechanic.MachineModel
import domain.mechanic.MachineState
import domain.mechanic.MachineType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.bogdan.machine_list.utils.getStateDescriptions
import ru.bogdan.machine_list.utils.getTypeDescriptions
import utils.SingleSharedFlow
import utils.exceptions.UnauthorizedException
import javax.inject.Inject

class MachineListViewModel @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val dispatcher: CoroutineDispatcher,
    private val resourceManager: ResourceManager,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {
    private val _state = MutableStateFlow(MachineListState())
    val state: StateFlow<MachineListState> = _state.asStateFlow()

    private val _uiAction = SingleSharedFlow<MachineListUiAction>()
    val uiAction = _uiAction.asSharedFlow()

    private val machines: MutableList<Machine> = mutableListOf()
    private val filters: MutableList<(Machine) -> Boolean> = mutableListOf()
    private val modelsForFilters: MutableList<MachineModel> = mutableListOf()
    private val statesForFilters: MutableList<MachineState> = mutableListOf()
    private val typesForFilters: MutableList<MachineType> = mutableListOf()
    private var yearsRangeFilter: IntRange = 1000..5000

    init {
        getMachines()
    }

    fun handleIntent(intent: MachineListIntent) {
        when (intent) {
            is MachineListIntent.ChangeFilterShowState -> {
                _state.update {
                    it.copy(isFiltersShow = !it.isFiltersShow)
                }
            }

            is MachineListIntent.SetMachineModelFilter -> {
                if (modelsForFilters.contains(intent.model)) {
                    modelsForFilters.remove(intent.model)
                } else {
                    modelsForFilters.add(intent.model)
                }
                _state.update {
                    it.copy(
                        filterState = it.filterState.copy(
                            modeFilter = modelsForFilters.toList()
                        )
                    )
                }
            }

            is MachineListIntent.SetMachineStateFilter -> {
                if (statesForFilters.contains(intent.state)) {
                    statesForFilters.remove(intent.state)
                } else {
                    statesForFilters.add(intent.state)
                }
                _state.update {
                    it.copy(
                        filterState = it.filterState.copy(
                            stateFilter = statesForFilters.toList()
                        )
                    )
                }
            }

            is MachineListIntent.SetMachineTypeFilter -> {
                if (typesForFilters.contains(intent.type)) {
                    typesForFilters.remove(intent.type)
                } else {
                    typesForFilters.add(intent.type)
                }
                _state.update {
                    it.copy(
                        filterState = it.filterState.copy(
                            typeFilter = typesForFilters.toList()
                        )
                    )
                }
            }

            is MachineListIntent.SetMachineYearFilter -> {
                yearsRangeFilter = intent.years
                _state.update {
                    it.copy(
                        filterState = it.filterState.copy(
                            yearFilter = yearsRangeFilter
                        )
                    )
                }
            }

            is MachineListIntent.DropFilters -> {
                modelsForFilters.clear()
                typesForFilters.clear()
                statesForFilters.clear()
                filters.clear()
                yearsRangeFilter = machines.getYearsRangeFromMachines()
                _state.update {
                    it.copy(
                        machines = machines,
                        filterState = MachineListFilterState(
                            typeDescriptions = getTypeDescriptions(resourceManager),
                            stateDescriptions = getStateDescriptions(resourceManager),
                            maxYear = machines.maxOfOrNull { it.yearOfManufacture } ?: 0,
                            minYear = machines.minOfOrNull { it.yearOfManufacture } ?: 0,
                            yearFilter = yearsRangeFilter
                        ),
                        isFiltersShow = false
                    )
                }
            }

            is MachineListIntent.ApplyFilters -> {
                _state.update {
                    it.copy(
                        isLoading = true,
                    )
                }
                viewModelScope.launch {
                    withContext(dispatcher) {
                        prepareFilters()
                        _state.update {
                            it.copy(
                                isLoading = false,
                                machines = machines.filter { machine ->
                                    filters.all { filter -> filter(machine) }
                                },
                                isFiltersShow = false
                            )
                        }
                    }
                }
            }

            MachineListIntent.RefreshData -> {
                refreshData()
            }
        }
    }

    private fun refreshData() {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true,
            )
            val result = withContext(dispatcher) {
                networkRepository.getMachines()
            }
            result.onSuccess { list ->
                machines.clear()
                machines.addAll(list)
                yearsRangeFilter = list.getYearsRangeFromMachines()
                _state.value = _state.value.copy(
                    isLoading = false,
                    machines = machines.filter { machine ->
                        filters.all { filter -> filter(machine) }
                    },
                )
            }.onFailure {
                if (it is UnauthorizedException) {
                    networkRepository.refreshToken(dataStoreManager.refreshToken.first() ?: "")
                        .onSuccess { responseToken ->
                            dataStoreManager.saveAccessTokens(
                                responseToken.accessToken,
                                responseToken.refreshToken
                            )
                        }.onFailure { _ ->
                            _uiAction.emit(MachineListUiAction.GoToLoginScreen)
                            _state.update { it.copy(isLoading = false) }
                        }
                }
                _state.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun getMachines() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            withContext(dispatcher) {
                networkRepository.getMachines()
                    .onSuccess { list ->
                        machines.addAll(list)
                        yearsRangeFilter = list.getYearsRangeFromMachines()
                        _state.value = _state.value.copy(
                            isLoading = false,
                            machines = machines.toList(),
                            filterState = MachineListFilterState(
                                typeDescriptions = getTypeDescriptions(resourceManager),
                                stateDescriptions = getStateDescriptions(resourceManager),
                                maxYear = machines.maxOfOrNull { it.yearOfManufacture } ?: 0,
                                minYear = machines.minOfOrNull { it.yearOfManufacture } ?: 0,
                                yearFilter = yearsRangeFilter
                            )
                        )
                    }.onFailure {
                        _uiAction.emit(MachineListUiAction.GoToLoginScreen)
                        _state.update { it.copy(isLoading = false) }
                    }
            }
        }
    }


    private fun prepareFilters() {
        if (modelsForFilters.isNotEmpty()) {
            filters.add {
                it.model in modelsForFilters
            }
        }
        if (typesForFilters.isNotEmpty()) {
            filters.add {
                it.type in typesForFilters
            }
        }
        if (statesForFilters.isNotEmpty()) {
            filters.add {
                it.state in statesForFilters
            }
        }
        filters.add {
            it.yearOfManufacture in yearsRangeFilter
        }
    }

    private fun List<Machine>.getYearsRangeFromMachines() =
        minBy { it.yearOfManufacture }.yearOfManufacture..maxBy { it.yearOfManufacture }.yearOfManufacture
}

