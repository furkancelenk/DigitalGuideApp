package com.celenko.digitalguide.ui.detail

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
class DetailViewModel @Inject constructor(
    private val placesRepository: PlacesRepository,
) : ViewModel() {

    private var _placeState = MutableLiveData(PlaceState(isLoading = true))
    val placeState: LiveData<PlaceState>
        get() = _placeState

    fun getPlaceDetail(id: Int) = viewModelScope.launch {
        when (val response = placesRepository.getPlaceDetail(id)) {
            is Resource.Success -> _placeState.value = PlaceState(isLoading = false, response.data)

            is Resource.Fail -> _placeState.value =
                PlaceState(isLoading = false, failMessage = response.message)

            is Resource.Error -> _placeState.value =
                PlaceState(isLoading = false, errorMessage = response.throwable.message)
        }
    }

    fun setFavoriteState() {
        val places = placeState.value?.places

        if (places?.isFavorite == true) {
            placesRepository.deleteFavorites(places.id ?: 0)
        } else {
            placesRepository.addFavorites(places!!)
        }

        getPlaceDetail(places.id ?: 1)
    }
}

data class PlaceState(
    val isLoading: Boolean = false,
    val places: Places? = null,
    val errorMessage: String? = null,
    val failMessage: String? = null,
)

