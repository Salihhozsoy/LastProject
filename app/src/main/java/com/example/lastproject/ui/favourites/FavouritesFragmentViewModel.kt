package com.example.lastproject.ui.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lastproject.data.locale.FavouriteEntity
import com.example.lastproject.data.repository.FavouriteRepository
import com.example.lastproject.data.state.FavouriteListState
import com.example.lastproject.ui.adapter.AdapterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesFragmentViewModel @Inject constructor(private val favouriteRepository: FavouriteRepository) :
    ViewModel() {

    private val _favouriteListState: MutableStateFlow<FavouriteListState> = MutableStateFlow(FavouriteListState.Idle)
    val favouriteListState: StateFlow<FavouriteListState> = _favouriteListState

    private val _adapterState: MutableStateFlow<AdapterState> = MutableStateFlow(AdapterState.Idle)
    val adapterState: StateFlow<AdapterState> = _adapterState


    fun getAllFavourites(userId:Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                _favouriteListState.value = FavouriteListState.Loading
                val favourites = favouriteRepository.getAllFavourites(userId)
                if (favourites.isEmpty()) _favouriteListState.value = FavouriteListState.Empty
                else _favouriteListState.value = FavouriteListState.Success(favourites.toMutableList())
            }.onFailure {
                _favouriteListState.value = FavouriteListState.Error(it)

            }
        }
    }

    fun removeFromFavourite(position:Int){
        viewModelScope.launch {
            kotlin.runCatching {
                if(_favouriteListState.value is FavouriteListState.Success){
                  val favourite = (_favouriteListState.value as FavouriteListState.Success).favourites[position]
                      (_favouriteListState.value as FavouriteListState.Success).favourites.removeAt(position)
                    favouriteRepository.delete(favourite)
                    _adapterState.value = AdapterState.Remove(position)

                }
            }
        }
    }
}