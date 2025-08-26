package ru.bogdan.login_feature.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.network.NetworkRepository
import domain.user.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val dispatcher: CoroutineDispatcher
): ViewModel() {
    private val _state = MutableStateFlow(LoginScreenState())
    val state: StateFlow<LoginScreenState> = _state.asStateFlow()

    fun handleIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.ChangeLogin -> {
                _state.update {it.copy(login = intent.login) }
            }
            is LoginIntent.ChangePassword -> {
                _state.update {it.copy(password = intent.password) }
            }
            LoginIntent.LogInPressed -> {
                viewModelScope.launch {
                    _state.update {it.copy( isLoading = true) }
                    var temp = User.NONE
                    withContext(dispatcher) {
                      temp =  networkRepository.login(
                          login = _state.value.login,
                          password = _state.value.password
                      )
                    }
                        _state.update {it.copy(login = temp.accessToken ?: "", isLoading = false) }
                }
            }
        }
    }
}