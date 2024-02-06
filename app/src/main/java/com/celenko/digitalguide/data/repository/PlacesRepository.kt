package com.celenko.digitalguide.data.repository

import com.celenko.digitalguide.common.Resource
import com.celenko.digitalguide.data.model.Category
import com.celenko.digitalguide.data.model.Places
import com.celenko.digitalguide.data.source.retrofit.PlaceService
import com.celenko.digitalguide.data.source.room.FavoritesDAO

class PlacesRepository(
    private val placeService: PlaceService,
    private val favoritesDAO: FavoritesDAO
) {
    suspend fun getPlaceDetail(id: Int): Resource<Places> {
        return try {
            val response = placeService.getPlaceDetail(id).body()

            if (response?.status == 200) {
                val favoriteIds = favoritesDAO.getFavoriteIds().orEmpty()

                Resource.Success(
                    Places(
                        id = response.detail.id,
                        title = response.detail.title,
                        description = response.detail.description,
                        imageUrl = response.detail.imageUrl,
                        location = response.detail.location,
                        rate = response.detail.rate,
                        isFavorite = favoriteIds.contains(response.detail.id ?: 0)
                    )
                )
            } else {
                Resource.Fail(response?.message)
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun getPlacesByCategories(category: String): Resource<List<Places>> {
        return try {
            val response = placeService.getPlacesByCategory(category).body()

            if (response?.status == 200) {
                Resource.Success(response.places.orEmpty())
            } else {
                Resource.Fail(response?.message.orEmpty())
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun getCategories(): Resource<List<Category>> {
        return try {
            val response = placeService.getCategories().body()

            if (response?.status == 200) {
                Resource.Success(response.categories.orEmpty())
            } else {
                Resource.Fail(response?.message.orEmpty())
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    fun getFavorites(): Resource<List<Places>> {
        return try {
            val response = favoritesDAO.getFavorites()

            if (response.isNullOrEmpty()) {
                Resource.Fail("Fail")
            } else {
                Resource.Success(response)
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    fun addFavorites(places: Places) {
        val placesFavorites = Places(
            id = places.id,
            title = places.title,
            description = places.description,
            imageUrl = places.imageUrl,
            rate = places.rate,
            location = places.location
        )

        favoritesDAO.addFavorites(placesFavorites)
    }

    fun deleteFavorites(placesId: Int) = favoritesDAO.deleteFavoritesWithId(placesId)
}