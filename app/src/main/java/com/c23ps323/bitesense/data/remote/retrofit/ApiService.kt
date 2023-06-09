package com.c23ps323.bitesense.data.remote.retrofit

import com.c23ps323.bitesense.data.remote.response.*
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("users/preference")
    suspend fun getUserHealthCondition(): HealthConditionResponse

    @GET("users/profile")
    suspend fun getUserProfile(): UserResponse

    @GET("products/lastscan")
    suspend fun getProductLastScan(): ProductResponse

    @GET("products/favorite")
    suspend fun getFavoriteProduct(): ProductResponse

    @GET("products/all")
    suspend fun getAllProduct(): ProductResponse

    @FormUrlEncoded
    @POST("users/login")
    suspend fun userLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @FormUrlEncoded
    @POST("users/register")
    suspend fun userRegister(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("repassword") repassword : String,
    ): RegisterResponse
}