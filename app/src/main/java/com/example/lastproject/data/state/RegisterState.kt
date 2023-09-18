package com.example.lastproject.data.state

sealed class RegisterState{
    object Idle:RegisterState()
    object Loading:RegisterState()
    object UserAlready:RegisterState()
    object Result:RegisterState()
    class Error(val throwable: Throwable?=null):RegisterState()
}
