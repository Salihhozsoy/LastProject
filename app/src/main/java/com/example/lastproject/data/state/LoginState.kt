package com.example.lastproject.data.state

sealed class LoginState{
    object Idle:LoginState()
    object Loading:LoginState()
    object UserNotFound:LoginState()
    class Success(val userId:Int):LoginState()
    class Error(val throwable: Throwable?=null):LoginState()
}
