package com.c23ps323.bitesense.data.remote.response

import com.google.gson.annotations.SerializedName


data class GenerateQrCode(
    @field:SerializedName("statusCode")
    val statusCode : Int? = null,

    @field:SerializedName("status")
    val status : String? = null,

    @field:SerializedName("message")
    val message : String? = null,

    @field:SerializedName("data")
    val data : String? = null
)
