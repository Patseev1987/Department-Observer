package ru.bogdan.machine_list.ui.machineDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.fileManager.FileManager
import data.network.NetworkRepository
import data.resorseMenager.ResourceManager
import domain.mechanic.Machine
import domain.mechanic.MachineDocument
import domain.mechanic.MachineState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.bogdan.core_ui.R
import ru.bogdan.core_ui.ui.common.extansions.getColor
import ru.bogdan.machine_list.di.MachineId
import ru.bogdan.machine_list.utils.getStateDescription
import ru.bogdan.machine_list.utils.getStateDescriptions
import ru.bogdan.machine_list.utils.getTypeDescription
import utils.SingleSharedFlow
import javax.inject.Inject

class MachineDetailsViewModel @Inject constructor(
    private val networkRepository: NetworkRepository,
    @MachineId private val machineId: String,
    private val resourceManager: ResourceManager,
    private val dispatcher: CoroutineDispatcher,
    private val fileManager: FileManager
) : ViewModel() {
    private val _state = MutableStateFlow(MachineDetailState())
    val state: StateFlow<MachineDetailState> = _state.asStateFlow()

    private val _uiAction = SingleSharedFlow<MachineDetailsUiAction>()
    val uiAction = _uiAction.asSharedFlow()

    private var machine: Machine = Machine.NONE
    private var doc : MachineDocument = MachineDocument.NONE
    private var downloadJob: Job? = null

    init {
        getMachine(machineId)
    }

    fun handleIntent(intent: MachineDetailsIntent) {
        when (intent) {
            is MachineDetailsIntent.ShowDialog -> {
                doc = intent.doc
                _state.update {
                    it.copy(
                        isShowDialog = true,
                        docName = doc.name,
                    )
                }
            }

            is MachineDetailsIntent.HideDialog -> {
                doc = MachineDocument.NONE
                _state.update {
                    it.copy(
                        isShowDialog = false,
                        docName = "",
                    )
                }
            }

            is MachineDetailsIntent.HideStates -> {

                _state.update {
                    it.copy(
                        expanded = false,
                    )
                }
            }

            is MachineDetailsIntent.SelectState -> {
                val machineState = intent.state.getMachineState(resourceManager)
                changeState(machineState, networkRepository)
                _state.update {
                    it.copy(
                        expanded = false,
                        state = intent.state,
                        color = machineState.getColor()
                    )
                }
            }

            is MachineDetailsIntent.ExpandChange -> {
                _state.update {
                    it.copy(
                        expanded = intent.value,
                    )
                }
            }

            is MachineDetailsIntent.SaveDoc -> {
                if(doc == MachineDocument.NONE) return
                saveFileToDownload(fileManager, doc)
                doc = MachineDocument.NONE
                _state.update {
                    it.copy(
                        isShowDialog = false,
                        docName = "",
                    )
                }
            }
        }
    }

    private fun getMachine(machineId: String) {
        viewModelScope.launch {
            val machines = networkRepository.getMachineById(machineId)
            machines.onSuccess {
                machine = it
                _state.value = _state.value.copy(
                    isLoading = false,
                    name = it.name,
                    model = it.model.name,
                    imageUrl = it.imageUrl,
                    description = it.description,
                    state = it.state.getStateDescription(resourceManager = resourceManager),
                    type = it.type.getTypeDescription(resourceManager = resourceManager),
                    yearOfManufacture = it.yearOfManufacture,
                    parts = it.parts,
                    docs = it.docs,
                    oils = it.oils,
                    color = it.state.getColor(),
                    states = getStateDescriptions(resourceManager = resourceManager),
                )
            }
                .onFailure { error ->
                    _uiAction.tryEmit(MachineDetailsUiAction.ShowToast(error.message ?: ""))
                }
        }
    }

    private fun changeState(state: MachineState, networkRepository: NetworkRepository) {
        viewModelScope.launch {
            withContext(dispatcher) {
                networkRepository.changeState(machine.copy(state = state))
            }
        }
    }

    private fun String.getMachineState(resourceManager: ResourceManager): MachineState = when (this) {
        resourceManager.getString(R.string.working) -> MachineState.WORKING

        resourceManager.getString(R.string.repair) -> MachineState.REPAIR

        resourceManager.getString(R.string.pause) -> MachineState.STOPPED

        else -> error("Unknown MachineState")
    }

    private fun saveFileToDownload(fileManager: FileManager, doc: MachineDocument) {
        if (downloadJob?.isActive == true) {
            downloadJob?.cancel()
        }
        downloadJob = viewModelScope.launch(CoroutineExceptionHandler { _, exception ->
                       _uiAction.tryEmit(
                MachineDetailsUiAction.ShowToast(
                    resourceManager.getString(
                        R.string.file_not_downloaded,
                        doc.name
                    )
                )
            )
            _state.update { it.copy(isLoading = false) }
        }) {
            _state.update { it.copy(isLoading = true) }
            val bytes = withContext(dispatcher) {
                networkRepository.downloadDocById(doc.id)
            }

            bytes.onSuccess {
                fileManager.saveFileInDownloads(doc.name, it)
                _uiAction.tryEmit(
                    MachineDetailsUiAction.ShowToast(
                        resourceManager.getString(
                            R.string.file_downloaded,
                            doc.name
                        )
                    )
                )
                _state.update { state -> state.copy(isLoading = false) }
            }
                .onFailure { throwable ->
                        throw throwable
                }
        }
    }

}