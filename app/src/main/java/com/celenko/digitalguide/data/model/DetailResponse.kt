package com.celenko.digitalguide.data.model

import com.google.gson.annotations.SerializedName

data class DetailResponse(
    @SerializedName("product") var detail: Places,
    @SerializedName("status") var status: Int?,
    @SerializedName("message") var message: String
)