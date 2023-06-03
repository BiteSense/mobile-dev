package com.c23ps323.bitesense.data.remote.retrofit

import com.c23ps323.bitesense.data.remote.response.ProductResponse
import com.c23ps323.bitesense.data.remote.response.UserResponse
import retrofit2.http.GET

interface ApiService {
    @GET("users/profile")
    suspend fun getUserProfile(): UserResponse

    @GET("products/lastscan")
    suspend fun getProductLastScan(): ProductResponse

    @GET("products/favorite")
    suspend fun getFavoriteProduct(): ProductResponse

    @GET("products/all")
    suspend fun getAllProduct(): ProductResponse
}