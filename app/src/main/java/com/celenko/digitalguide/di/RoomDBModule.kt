package com.celenko.digitalguide.di

import android.content.Context
import androidx.room.Room
import com.celenko.digitalguide.data.source.room.FavoritesDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomDBModule {

    @Provides
    @Singleton
    fun provideRoomDB(
        @ApplicationContext context: Context
    ): FavoritesDB = Room.databaseBuilder(
        context,
        FavoritesDB::class.java,
        "guidefavoritesdatabase",
    ).allowMainThreadQueries().build()

    @Provides
    @Singleton
    fun provideGuideFavoritesDao(
        favoritesDB: FavoritesDB
    ) = favoritesDB.favoritesDAO()
}