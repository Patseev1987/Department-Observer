package ru.bogdan.main_screen_feature.ui.homeScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.DataStoreManager
import data.NetworkRepository
import data.ResourceManager
import domain.mechanic.Machine
import domain.mechanic.MachineState
import domain.user.Role
import domain.user.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.bogdan.core_ui.R
import ru.bogdan.core_ui.ui.common.extansions.getColor
import utils.SingleSharedFlow
import javax.inject.Inject

class HomeScreenViewModel @Inject constructor(
    private val manager: ResourceManager,
    private val dispatcher: CoroutineDispatcher,
    private val networkRepository: NetworkRepository,
    private val dataStoreManager: DataStoreManager,

    ) : ViewModel() {

    private var machines: Result<List<Machine>> = Result.success(emptyList())
    private var user: Result<User> = Result.success(User.NONE)

    private val _state = MutableStateFlow(HomeScreenState())
    val state = _state.asStateFlow()

    private val _uiAction = SingleSharedFlow<HomeScreenUiAction>()
    val uiAction = _uiAction.asSharedFlow()

    init {
        prepareData()
    }

    fun handleIntent(intent: HomeScreenIntent) {
        when (intent) {
            is HomeScreenIntent.ShowRepairList -> {
                _state.update { it.copy(isShowRepairList = intent.isShow) }
            }

            is HomeScreenIntent.ShowInfoList -> {
                _state.update { it.copy(isShowInfoList = intent.isShow) }
            }
        }
    }

    private fun prepareData() = viewModelScope.launch {
        val tempUser = async { networkRepository.getUser() }
        val tempMachines = async { networkRepository.getMachines() }
        val tempInfo = async { networkRepository.getInfo() }
        withContext(dispatcher) {
            user = tempUser.await()
            machines = tempMachines.await()
            user.onSuccess { user ->
                machines.onSuccess { machines ->
                    _state.update {
                        it.copy(
                            name = user.name,
                            surname = user.surname,
                            patronymic = user.patronymic,
                            photo = user.photoUrl,
                            role = getStringFromRole(user.role),
                            isLoading = false,
                            infoAboutMachines = getInfoAboutMachines(machines),
                            machines = machines,
                            repairList = machines.filter { it.state == MachineState.REPAIR },
                            info = tempInfo.await().getOrThrow()
                        )
                    }
                }
                    .onFailure { throwable ->
                        throw throwable
                    }
            }
                .onFailure { throwable ->
                    _state.update { it.copy(isLoading = false) }
                    _uiAction.emit(HomeScreenUiAction.ShowToast(throwable.message ?: ""))
                }
        }
    }

    private fun getInfoAboutMachines(machines: List<Machine>): List<InfoAboutMachines> {
        val tempInfo = mutableListOf<InfoAboutMachines>()
        val angle = WHOLE_CIRCLE / machines.size
        MachineState.entries.forEach { state ->
            val count = machines.count { it.state == state }
            tempInfo.add(
                InfoAboutMachines(
                    state = state,
                    percentage = count * angle,
                    title = getTitle(state),
                    color = state.getColor(),
                    count = count
                )
            )
        }
        return tempInfo
    }

    private fun getTitle(state: MachineState): String = when (state) {
        MachineState.WORKING -> manager.getString(R.string.working)
        MachineState.STOPPED -> manager.getString(R.string.pause)
        MachineState.REPAIR -> manager.getString(R.string.repair)
    }

    private fun getStringFromRole(role: Role): String = when (role) {
        Role.MANAGER -> manager.getString(R.string.role_manager)
        Role.MECHANIC -> manager.getString(R.string.role_mechanic)
    }

    companion object {
        const val WHOLE_CIRCLE = 360f
    }
}