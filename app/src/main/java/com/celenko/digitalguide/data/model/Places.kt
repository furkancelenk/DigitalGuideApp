package com.celenko.digitalguide.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "favorites")
@Parcelize
data class Places(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int?,

    @ColumnInfo(name = "title")
    val title: String?,

    @ColumnInfo(name = "description")
    val description: String?,

    @ColumnInfo(name = "imageUrl")
    @SerializedName("imageOne")
    val imageUrl: String?,

    @ColumnInfo(name = "location")
    @SerializedName("imageTwo")
    val location: String?,

    @ColumnInfo(name = "rate")
    val rate: Double?,

    val isFavorite: Boolean = false
) : Parcelable
