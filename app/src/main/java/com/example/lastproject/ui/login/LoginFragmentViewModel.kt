package com.example.lastproject.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lastproject.data.repository.LoginRepository
import com.example.lastproject.data.state.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginFragmentViewModel @Inject constructor(private val loginRepository: LoginRepository):ViewModel() {

    private val _loginState: MutableStateFlow<LoginState> = MutableStateFlow(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    private val _message: MutableSharedFlow<String> = MutableSharedFlow()
    val message: SharedFlow<String> = _message

    fun login(emailOrUsername: String, password: String) {
        viewModelScope.launch {
            kotlin.runCatching {

                if (emailOrUsername.isNotEmpty() && password.isNotEmpty()) {
                    _loginState.value = LoginState.Loading

                    if(emailOrUsername.contains("@")){

                        if (loginRepository.loginByEmail(emailOrUsername, password) == null) {
                            _loginState.value =LoginState.UserNotFound
                        } else {
                            _loginState.value = LoginState.Success
                            _message.emit("giriş başarılı")
                        }
                    }
                    else{
                        if (loginRepository.loginByUsername(emailOrUsername, password) == null) {
                            _loginState.value =LoginState.UserNotFound
                        } else {
                            _loginState.value = LoginState.Success
                            _message.emit("giriş başarılı")
                        }
                    }

                } else {
                    _message.emit("Boş alan bırakma")
                }
            }.onFailure {
                _loginState.value = LoginState.Error(it)
            }
        }
    }
}