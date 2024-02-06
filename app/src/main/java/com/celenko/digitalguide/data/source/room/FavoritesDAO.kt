package com.celenko.digitalguide.data.source.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.celenko.digitalguide.data.model.Places

@Dao
interface FavoritesDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFavorites(places: Places)

    @Query("SELECT * FROM favorites")
    fun getFavorites(): List<Places>?

    @Query("DELETE FROM favorites WHERE id = :idInput")
    fun deleteFavoritesWithId(idInput: Int)

    @Query("DELETE FROM favorites")
    fun clearFavorites()

    @Query("SELECT id FROM favorites")
    fun getFavoriteIds(): List<Int>?
}