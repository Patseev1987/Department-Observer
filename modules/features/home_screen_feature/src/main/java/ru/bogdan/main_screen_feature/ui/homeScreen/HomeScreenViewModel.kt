package ru.bogdan.main_screen_feature.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.network.NetworkRepository
import data.resorseMenager.ResourceManager
import domain.mechanic.Machine
import domain.user.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.bogdan.main_screen_feature.R
import ru.bogdan.main_screen_feature.di.UserId
import ru.bogdan.main_screen_feature.ui.homeScreen.HomeScreenIntent
import ru.bogdan.main_screen_feature.ui.homeScreen.HomeScreenState
import ru.bogdan.main_screen_feature.ui.homeScreen.HomeScreenUiAction
import utils.SingleSharedFlow
import javax.inject.Inject

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

        if (user.isSuccess && machines.isSuccess) {
            _state.update {
                it.copy(
                    name = user.getOrDefault(User.NONE).name,
                    surname = user.getOrDefault(User.NONE).surname,
                    patronymic = user.getOrDefault(User.NONE).patronymic,
                    photo = user.getOrDefault(User.NONE).photoUrl,
                    navItems = getNavigateItem(),
                    isLoading = false,
                    machines = machines.getOrDefault(listOf(Machine.NONE)),
                )
            }
        } else {
            _state.update { it.copy(isLoading = false) }
            user.exceptionOrNull()?.let{
                _uiAction.emit(HomeScreenUiAction.ShowToast(it.message ?: ""))
            }
            machines.exceptionOrNull()?.let{
                _uiAction.emit(HomeScreenUiAction.ShowToast(it.message ?: ""))
            }
        }
    }

    private fun getNavigateItem(): List<ObserverNavigationItem> = listOf(
        ObserverNavigationItem.HomeItem(
            title = manager.getString(R.string.home),
            drawableId = R.drawable.home_screen
        ),
        ObserverNavigationItem.MachinesItem(
            title = manager.getString(R.string.machines),
            drawableId = R.drawable.machines
        ),
        ObserverNavigationItem.WarehousItem(
            title = manager.getString(R.string.mechanic_warehouse),
            drawableId = R.drawable.mechanic_warehouse
        ),
        ObserverNavigationItem.MapItem(
            title = manager.getString(R.string.department_map),
            drawableId = R.drawable.department_map
        )
    )
}