package com.celenko.digitalguide.di

import com.celenko.digitalguide.data.repository.PlacesRepository
import com.celenko.digitalguide.data.source.retrofit.PlaceService
import com.celenko.digitalguide.data.source.room.FavoritesDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun providePlacesRepository(
        placeService: PlaceService,
        favoritesDAO: FavoritesDAO
    ) = PlacesRepository(placeService, favoritesDAO)
}