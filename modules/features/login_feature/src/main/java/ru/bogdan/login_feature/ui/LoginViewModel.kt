package ru.bogdan.login_feature.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.DataStoreManager
import data.NetworkRepository
import data.ResourceManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.bogdan.core_ui.R
import utils.SingleSharedFlow
import utils.exceptions.UnauthorizedException
import javax.inject.Inject

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
                    delay(1000)
                    withContext(dispatcher) {
                        networkRepository.login(
                            login = _state.value.login,
                            password = _state.value.password
                        )
                            .onSuccess { loginResponse ->
                                dataStoreManager.saveAccessTokens(
                                    loginResponse.token,
                                    loginResponse.refreshToken
                                )
                                _state.update { it.copy(isLoading = false) }
                                _uiAction.emit(
                                    LoginUiAction.ShowToast(
                                        resourceManager.getString(R.string.succes, loginResponse.username)
                                    )
                                )
                                delay(400)
                                _uiAction.emit(LoginUiAction.GoToMainScreen)
                            }
                            .onFailure { error ->
                                _uiAction.emit(LoginUiAction.ShowToast(error.message ?: ""))
                                _state.update { it.copy(isLoading = false) }
                            }
                    }
                }
            }
        }
    }
}