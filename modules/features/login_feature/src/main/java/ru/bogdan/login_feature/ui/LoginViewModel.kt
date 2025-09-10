package ru.bogdan.login_feature.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.dataStore.DataStoreManager
import data.network.NetworkRepository
import data.resorseMenager.ResourceManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import utils.SingleSharedFlow
import javax.inject.Inject
import ru.bogdan.core_ui.R

class LoginViewModel @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val dataStoreManager: DataStoreManager,
    private val resourceManager: ResourceManager,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _state = MutableStateFlow(LoginScreenState())
    val state: StateFlow<LoginScreenState> = _state.asStateFlow()

    private val _uiAction = SingleSharedFlow<LoginUiAction>()
    val uiAction = _uiAction.asSharedFlow()

    fun handleIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.ChangeLogin -> {
                _state.update { it.copy(login = intent.login) }
            }

            is LoginIntent.ChangePassword -> {
                _state.update { it.copy(password = intent.password) }
            }

            LoginIntent.LogInPressed -> {
                viewModelScope.launch {
                    _state.update { it.copy(isLoading = true) }

                    withContext(dispatcher) {
                        networkRepository.login(
                            login = _state.value.login,
                            password = _state.value.password
                        )
                            .onSuccess {
                                dataStoreManager.saveAccessTokens(it.accessToken, it.refreshToken)
                                _state.update { it.copy(isLoading = false) }
                                Log.i("Login", "Login successful ${it.name}")
                                _uiAction.emit(LoginUiAction.ShowToast(
                                    resourceManager.getString(R.string.succes, it.name)
                                ))
                                delay(1000)
                                _uiAction.emit(LoginUiAction.GoToMainScreen(it.id))
                            }
                            .onFailure {error ->
                                _uiAction.emit(LoginUiAction.ShowToast(error.message ?: ""))
                                Log.i("Login", "Login  ${error.message}")
                                _state.update { it.copy(isLoading = false) }
                            }
                    }
                }
            }
        }
    }
}