package ru.bogdan.main_screen_feature.ui.homeScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.network.NetworkRepository
import data.resorseMenager.ResourceManager
import domain.mechanic.Machine
import domain.mechanic.MachineState
import domain.user.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.bogdan.main_screen_feature.di.UserId
import utils.SingleSharedFlow
import javax.inject.Inject
import ru.bogdan.main_screen_feature.R
import ru.bogdan.main_screen_feature.ui.homeScreen.InfoAboutMachines.Companion.getColor

class HomeScreenViewModel @Inject constructor(
    private val manager: ResourceManager,
    private val dispatcher: CoroutineDispatcher,
    private val networkRepository: NetworkRepository,
    @UserId private val userId: String
) : ViewModel() {

    private var machines: Result<List<Machine>> = Result.success(emptyList())
    private var user: Result<User> = Result.success(User.NONE)


    private val _state = MutableStateFlow(HomeScreenState())
    val state = _state.asStateFlow()

    private val _uiAction = SingleSharedFlow<HomeScreenUiAction>()
    val uiAction = _uiAction.asSharedFlow()

    init {
        prepareData(userId)
    }

    fun handleIntent(intent: HomeScreenIntent) {
        when (intent) {
            is HomeScreenIntent.NavItemClicked -> {

            }
        }
    }

    private fun prepareData(userId: String) = viewModelScope.launch(dispatcher) {
        val tempUser = async { networkRepository.getUserById(userId) }
        val tempMachines = async { networkRepository.getMachines() }
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
                        isLoading = false,
                        infoAboutMachines = getInfoAboutMachines(machines),
                        machines = machines,
                    )
                }
            }
                .onFailure { throwable ->
                    _uiAction.emit(HomeScreenUiAction.ShowToast(throwable.message ?: ""))
                }
        }
            .onFailure { throwable ->
                _uiAction.emit(HomeScreenUiAction.ShowToast(throwable.message ?: ""))
            }
    }

    private fun getInfoAboutMachines(machines: List<Machine>): List<InfoAboutMachines>{
        val tempInfo = mutableListOf<InfoAboutMachines>()
        val angle = WHOLE_CIRCLE/machines.size
        MachineState.entries.forEach { state ->
            tempInfo.add(InfoAboutMachines(
                state = state,
                percentage = machines.count { it.state == state } * angle,
                title = getTitle(state),
                color = state.getColor(),
            ))
        }
        return tempInfo
    }

    private fun getTitle(state: MachineState): String = when (state) {
        MachineState.WORKING -> manager.getString(R.string.working)
        MachineState.STOPPED -> manager.getString(R.string.pause)
        MachineState.REPAIR -> manager.getString(R.string.repair)
    }

    companion object {
        const val WHOLE_CIRCLE = 360f
    }
}