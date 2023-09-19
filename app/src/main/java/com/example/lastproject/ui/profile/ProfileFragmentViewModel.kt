package com.example.lastproject.ui.profile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lastproject.data.locale.entity.UserEntity
import com.example.lastproject.data.repository.RegisterRepository
import com.example.lastproject.data.repository.UserRepository
import com.example.lastproject.data.state.GetProfileState
import com.example.lastproject.data.state.UpdateProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileFragmentViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val registerRepository: RegisterRepository
) :
    ViewModel() {

    private val _getProfileState: MutableStateFlow<GetProfileState> =
        MutableStateFlow(GetProfileState.Idle)
    val getProfileState: StateFlow<GetProfileState> = _getProfileState

    private val _updateProfileState: MutableStateFlow<UpdateProfileState> =
        MutableStateFlow(UpdateProfileState.Idle)
    val updateProfileState: StateFlow<UpdateProfileState> = _updateProfileState

    private val _message: MutableSharedFlow<String> = MutableSharedFlow()
    val message: SharedFlow<String> = _message

    fun getProfileInfo(id: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                _getProfileState.value = GetProfileState.Loading
                val post = userRepository.getProfileById(id)
                _getProfileState.value = GetProfileState.Success(post)

            }.onFailure {
                _getProfileState.value = GetProfileState.Error
            }
        }
    }
    fun updateProfilePhoto(uri: Uri){
        viewModelScope.launch {
            runCatching {
                _updateProfileState.value = UpdateProfileState.Loading
                val user = if(_getProfileState.value is GetProfileState.Success) (_getProfileState.value as GetProfileState.Success).user
                else null
                user?.let {
                    val user=it.copy(profileImageUrl = uri.toString())
                    userRepository.updateProfile(user)
                    _updateProfileState.value = UpdateProfileState.Success
                    _updateProfileState.value = UpdateProfileState.Idle
                }
            }
        }
    }

    fun updateProfile(username: String, email: String, password: String) {
        viewModelScope.launch {
            if (username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                kotlin.runCatching {
                    _updateProfileState.value = UpdateProfileState.Loading

                    if (registerRepository.getUserByUsername(username) == null && registerRepository.getUserByEmail(email) == null) {
                        val user = if(_getProfileState.value is GetProfileState.Success) (_getProfileState.value as GetProfileState.Success).user
                        else null
                        user?.let {
                           val user=it.copy(username=username, email = email, password = password)
                            userRepository.updateProfile(user)
                            _updateProfileState.value = UpdateProfileState.Success
                            _updateProfileState.value = UpdateProfileState.Idle
                        }

                    } else {
                        _message.emit("lütfen farklı bir email ya da kullanıcı adı giriniz")
                        _updateProfileState.value = UpdateProfileState.AlreadyUser
                    }
                }.onFailure {
                    _updateProfileState.value = UpdateProfileState.Error
                    _message.emit("güncelleme işlemi yapılamadı")
                }
            } else {
                _message.emit("boş alan bırakmayınız")
            }

        }
    }
}