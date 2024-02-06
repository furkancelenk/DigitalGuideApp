package com.celenko.digitalguide.data.model

import com.google.gson.annotations.SerializedName

data class GetCategoriesResponse(
    @SerializedName("categories") var categories: List<Category>?,
    @SerializedName("status") var status: Int?,
    @SerializedName("message") var message: String?
)