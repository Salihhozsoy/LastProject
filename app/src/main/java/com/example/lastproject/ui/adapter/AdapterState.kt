package com.example.lastproject.ui.adapter

sealed class AdapterState{
    object Idle:AdapterState()
    class Remove(val position:Int):AdapterState()
    object Error:AdapterState()
}
