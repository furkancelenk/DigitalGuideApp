package com.celenko.digitalguide.data.source.retrofit

import com.celenko.digitalguide.data.model.DetailResponse
import com.celenko.digitalguide.data.model.GetCategoriesResponse
import com.celenko.digitalguide.data.model.PlacesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceService {

    @GET("get_categories")
    suspend fun getCategories(): Response<GetCategoriesResponse>

    @GET("get_products_by_category")
    suspend fun getPlacesByCategory(@Query("category") category: String): Response<PlacesResponse>

    @GET("get_product_detail")
    suspend fun getPlaceDetail(@Query("id") id: Int): Response<DetailResponse>
}