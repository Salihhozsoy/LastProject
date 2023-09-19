package com.example.lastproject.data.state

sealed class UpdateProfileState{
    object Idle:UpdateProfileState()
    object Loading:UpdateProfileState()
    object Success:UpdateProfileState()
    object AlreadyUser:UpdateProfileState()
    object Error:UpdateProfileState()
}
