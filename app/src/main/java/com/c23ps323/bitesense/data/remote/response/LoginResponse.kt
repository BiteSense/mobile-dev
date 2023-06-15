package com.c23ps323.bitesense.data.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @field:SerializedName("data")
    val data: DataToken? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("statusCode")
    val statusCode: Int? = null,

    @field:SerializedName("status")
    val status: String? = null
)

data class DataToken(

    @field:SerializedName("token")
    val token: String? = null
)
