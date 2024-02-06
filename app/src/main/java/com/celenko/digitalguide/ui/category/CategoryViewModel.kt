package com.celenko.digitalguide.ui.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.celenko.digitalguide.common.Resource
import com.celenko.digitalguide.data.model.Category
import com.celenko.digitalguide.data.repository.PlacesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val placesRepo: PlacesRepository
) : ViewModel() {

    private var _categoryState = MutableLiveData(CategoryState(isLoading = true))
    val categoryState: LiveData<CategoryState>
        get() = _categoryState

    fun getCategories() {
        viewModelScope.launch {
            when (val response = placesRepo.getCategories()) {
                is Resource.Success -> {
                    _categoryState.value =
                        CategoryState(isLoading = false, categoriesList = response.data)
                }

                is Resource.Fail -> {
                    _categoryState.value =
                        CategoryState(isLoading = false, failMessage = response.message)
                }

                is Resource.Error -> {
                    _categoryState.value =
                        CategoryState(isLoading = false, errorMessage = response.throwable.message)
                }
            }
        }
    }
}

data class CategoryState(
    val isLoading: Boolean = false,
    val categoriesList: List<Category>? = null,
    val errorMessage: String? = null,
    val failMessage: String? = null,
)