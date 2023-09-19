package com.example.lastproject.data.state

import com.example.lastproject.data.locale.entity.UserEntity

sealed class GetProfileState{

    object Idle: GetProfileState()
    object Loading: GetProfileState()
    class Success(val user: UserEntity): GetProfileState()
    object Error: GetProfileState()
}
