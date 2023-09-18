package com.example.lastproject.data.state

import com.example.lastproject.data.locale.UserEntity

sealed class GetProfileState{

    object Idle: GetProfileState()
    object Loading: GetProfileState()
    class Success(val user:UserEntity): GetProfileState()
    object Error: GetProfileState()
}
