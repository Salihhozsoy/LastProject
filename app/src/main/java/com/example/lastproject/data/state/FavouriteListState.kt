package com.example.lastproject.data.state

import com.example.lastproject.data.locale.FavouriteEntity

sealed class FavouriteListState {
    object Idle : FavouriteListState()
    object Empty : FavouriteListState()
    object Loading : FavouriteListState()
    class Success(val favourites: MutableList<FavouriteEntity>) : FavouriteListState()
    class Error(val throwable: Throwable) : FavouriteListState()
}
