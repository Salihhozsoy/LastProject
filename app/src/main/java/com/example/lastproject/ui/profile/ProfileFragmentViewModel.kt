package com.example.lastproject.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lastproject.data.locale.UserEntity
import com.example.lastproject.data.repository.UserRepository
import com.example.lastproject.data.state.GetProfileState
import com.example.lastproject.data.state.UpdateProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileFragmentViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {

    private val _getProfileState: MutableStateFlow<GetProfileState> =
        MutableStateFlow(GetProfileState.Idle)
    val getProfileState: StateFlow<GetProfileState> = _getProfileState

    private val _updateProfileState: MutableStateFlow<UpdateProfileState> =
        MutableStateFlow(UpdateProfileState.Idle)
    val updateProfileState: StateFlow<UpdateProfileState> = _updateProfileState

    fun getPost(id: Int) {
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

    fun updateProfile(usernameOrUserName: String ,password: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                _updateProfileState.value = UpdateProfileState.Loading

                if(usernameOrUserName.contains("@")){
                    val user = UserEntity(username = "", email = usernameOrUserName, password = password)
                    userRepository.updateProfile(user)
                }else{
                    val user=UserEntity(username = usernameOrUserName, email = "", password = password )
                    userRepository.updateProfile(user)
                }
                _updateProfileState.value = UpdateProfileState.Success
            }
        }
    }
}