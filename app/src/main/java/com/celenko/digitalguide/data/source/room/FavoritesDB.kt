package com.celenko.digitalguide.data.source.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.celenko.digitalguide.data.model.Places

@Database(entities = [Places::class], version = 2)
abstract class FavoritesDB : RoomDatabase() {
    abstract fun favoritesDAO(): FavoritesDAO
}