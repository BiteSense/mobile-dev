package com.c23ps323.bitesense.data.remote.retrofit

import com.c23ps323.bitesense.data.remote.response.*
import retrofit2.http.*

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
    @POST("qrcode/inputProduct")
    suspend fun createQrCode(
        @Header("Authorization") token: String,
        @Field("nama_produk") nama_produk : String,
        @Field("komposisi_produk") komposisi_produk : String,
        @Field("expired") expired : String,
        @Field("tgl_produksi") tgl_produksi : String,
    ) : GenerateQrCode

    @FormUrlEncoded
    @POST("users/login")
    suspend fun userLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @FormUrlEncoded
    @POST("users/register")
    suspend fun userRegister(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("repassword") repassword : String,
        @Field("username") name: String,
    ): RegisterResponse
}