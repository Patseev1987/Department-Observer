package ru.bogdan.main_screen_feature.ui.homeScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.network.NetworkRepository
import data.resorseMenager.ResourceManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.bogdan.main_screen_feature.R
import ru.bogdan.main_screen_feature.di.UserId
import ru.bogdan.main_screen_feature.ui.ObserverNavigationItem
import utils.SingleSharedFlow
import javax.inject.Inject

class HomeScreenViewModel @Inject constructor(
    private val manager: ResourceManager,
    private val dispatcher: CoroutineDispatcher,
    private val networkRepository: NetworkRepository,
    @UserId private val userId: String
) : ViewModel() {
    private val _state = MutableStateFlow(HomeScreenState())
    val state = _state.asStateFlow()

    private val _uiAction = SingleSharedFlow<HomeScreenUiAction>()
    val uiAction = _uiAction.asSharedFlow()

    init {
        getUser(userId)
    }

    fun handleIntent(intent: HomeScreenIntent) {
        when (intent) {
            is HomeScreenIntent.NavItemClicked -> {

            }
        }
    }

    private fun getUser(userId: String) = viewModelScope.launch(dispatcher) {
        networkRepository.getUserById(userId)
            .onSuccess { user ->
                _state.update {
                    it.copy(
                        name = user.name,
                        surname = user.surname,
                        patronymic = user.patronymic,
                        photo = user.photoUrl,
                        navItems = getNavigateItem()
                    )
                }
            }
            .onFailure {
                _uiAction.emit(HomeScreenUiAction.ShowToast(it.message.toString()))

            }
    }

    private fun getNavigateItem(): List<ObserverNavigationItem> = listOf(
        ObserverNavigationItem.HomeItem(
            title = manager.getString(R.string.home),
            drawableId = R.drawable.home_screen
        ),
        ObserverNavigationItem.WarehousNameItem(
            title = manager.getString(R.string.machines),
            drawableId = R.drawable.machines
        ),
        ObserverNavigationItem.WarehousNameItem(
            title = manager.getString(R.string.mechanic_warehouse),
            drawableId = R.drawable.mechanic_warehouse
        ),
        ObserverNavigationItem.WarehousNameItem(
            title = manager.getString(R.string.department_map),
            drawableId = R.drawable.department_map
        )
    )
}