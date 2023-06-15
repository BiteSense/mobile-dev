package com.c23ps323.bitesense.data.remote.retrofit

import com.c23ps323.bitesense.data.remote.response.EditProfileResponse
import com.c23ps323.bitesense.data.remote.response.HealthConditionResponse
import com.c23ps323.bitesense.data.remote.response.LoginResponse
import com.c23ps323.bitesense.data.remote.response.ProductResponse
import com.c23ps323.bitesense.data.remote.response.UploadProductResponse
import com.c23ps323.bitesense.data.remote.response.UserResponse
import com.google.gson.JsonElement
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @POST("users/preference/update")
    suspend fun updatePreference(
        @Body preferences: JsonElement
    ): UploadProductResponse

    @FormUrlEncoded
    @POST("users/register")
    suspend fun register(
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("repassword") repassword: String
    ): UploadProductResponse

    @FormUrlEncoded
    @POST("users/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<LoginResponse>

    @GET("products/scan")
    suspend fun getScannedProducts(): ProductResponse

    @FormUrlEncoded
    @POST("users/profile/telepon")
    suspend fun editTelepon(
        @Field("telepon") telepon: String
    ): EditProfileResponse

    @FormUrlEncoded
    @POST("users/profile/email")
    suspend fun editEmail(
        @Field("email") email: String
    ): EditProfileResponse

    @FormUrlEncoded
    @POST("users/profile/username")
    suspend fun editUsername(
        @Field("username") username: String
    ): EditProfileResponse

    @Multipart
    @POST("products/upload")
    suspend fun uploadProduct(
        @Part file: MultipartBody.Part
    ): Response<UploadProductResponse>

    @GET("users/preference")
    suspend fun getUserHealthCondition(): HealthConditionResponse

    @GET("users/profile")
    suspend fun getUserProfile(): UserResponse

    @GET("products/all")
    suspend fun getAllProduct(): ProductResponse
}