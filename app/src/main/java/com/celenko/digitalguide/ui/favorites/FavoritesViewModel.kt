package com.celenko.digitalguide.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.celenko.digitalguide.common.Resource
import com.celenko.digitalguide.data.model.Places
import com.celenko.digitalguide.data.repository.PlacesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val placesRepository: PlacesRepository
) : ViewModel() {
    private var _favoritesState = MutableLiveData(FavoritesState())
    val favoritesState: LiveData<FavoritesState>
        get() = _favoritesState

    fun getFavorites() {
        viewModelScope.launch {
            when (val response = placesRepository.getFavorites()) {
                is Resource.Success -> {
                    _favoritesState.value = FavoritesState(favoritesList = response.data)
                }

                is Resource.Fail -> {
                    _favoritesState.value =
                        FavoritesState(failMessage = response.message)
                }

                is Resource.Error -> {
                    _favoritesState.value =
                        FavoritesState(errorMessage = response.throwable.message)
                }
            }
        }
    }

    fun deleteFavorites(placesId: Int) {
        placesRepository.deleteFavorites(placesId)
        getFavorites()
    }
}

data class FavoritesState(
    val favoritesList: List<Places>? = null,
    val errorMessage: String? = null,
    val failMessage: String? = null

)