package com.example.lastproject.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lastproject.data.locale.UserEntity
import com.example.lastproject.data.repository.RegisterRepository
import com.example.lastproject.data.state.RegisterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterFragmentViewModel @Inject constructor(private val registerRepository: RegisterRepository):ViewModel() {

    private val _registerState: MutableStateFlow<RegisterState> = MutableStateFlow(RegisterState.Idle)
    val registerState: StateFlow<RegisterState> = _registerState

    private val _message: MutableSharedFlow<String> = MutableSharedFlow()
    val message: SharedFlow<String> = _message

    fun register(emailOrUsername: String, password: String, confirm: String) {
        viewModelScope.launch {

                if (emailOrUsername.isNotEmpty() && password.isNotEmpty() && confirm.isNotEmpty()) {
                    _registerState.value = RegisterState.Loading

                    if(emailOrUsername.contains("@")){
                        kotlin.runCatching {
                        if(registerRepository.getUserByEmail(emailOrUsername) == null){
                            if(password==confirm){
                                val user = UserEntity(email = emailOrUsername, password = password, username = "x")
                                registerRepository.insert(user)
                                _registerState.value =RegisterState.Result
                                _message.emit("kullanıcı email ile eklendi")
                            }else{
                                _message.emit("şifreler eşleşmiyor")
                            }

                        }else{
                            _registerState.value = RegisterState.UserAlready
                            _message.emit("bu maile sahip kullanıcı zaten var")
                        }
                        }.onFailure {
                            _registerState.value = RegisterState.Error(it)
                            _message.emit("kullanıcı eklenirken sorun oluştu $it")
                        }
                    }
                    else{
                        if(registerRepository.getUserByUsername(emailOrUsername) == null){
                            if(password==confirm){
                                val user = UserEntity(email = "x", password = password, username = emailOrUsername)
                                registerRepository.insert(user)
                                _registerState.value =RegisterState.Result
                                _message.emit("kullanıcı username ile eklendi")
                            }else{
                                _message.emit("şifreler eşleşmiyor")
                            }

                        }else{
                            _registerState.value = RegisterState.UserAlready
                            _message.emit("bu username sahip kullanıcı zaten var")
                        }
                    }

                } else {
                    _message.emit("boş alan bırakmayınız")
                }

        }
    }
}