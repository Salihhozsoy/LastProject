package com.example.lastproject.data.state

sealed class LoginState{
    object Idle:LoginState()
    object Loading:LoginState()
    object UserNotFound:LoginState()
    object Success:LoginState()
    class Error(val throwable: Throwable?=null):LoginState()
}
