package com.c23ps323.bitesense.data.retrofit

import com.c23ps323.bitesense.data.response.ProductResponse
import retrofit2.http.GET

interface ApiService {
    @GET("products/lastscan")
    suspend fun getProductLastScan(): ProductResponse

    @GET("products/favorite")
    suspend fun getFavoriteProduct(): ProductResponse

    @GET("products/all")
    suspend fun getAllProduct(): ProductResponse
}