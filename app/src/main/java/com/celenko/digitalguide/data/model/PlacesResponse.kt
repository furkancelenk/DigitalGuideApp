package com.celenko.digitalguide.data.model

import com.google.gson.annotations.SerializedName

data class PlacesResponse(
    @SerializedName("products") var places: List<Places>?,
    @SerializedName("status") var status: Int?,
    @SerializedName("message") var message: String?
)
