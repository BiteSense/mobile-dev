package com.c23ps323.bitesense.data.remote.response

import com.google.gson.annotations.SerializedName

data class ScanQRResponse(
    @field:SerializedName("statusCode")
    val statusCode : Int? = null,

    @field:SerializedName("status")
    val status : String? = null,

    @field:SerializedName("message")
    val message : String? = null,

    @field:SerializedName("data")
    val data : ArrayList<ScanData> = arrayListOf()
)
data class ScanData(
    @field:SerializedName("id_produk")
    val id_produk : Int? = null,

    @field:SerializedName("nama_produk")
    val nama_produk : String? = null,

    @field:SerializedName("komposisi_produk")
    val komposisi_produk : String? = null,

    @field:SerializedName("tgl_produksi")
    val tgl_produksi : String? = null,

    @field:SerializedName("expired")
    val expired : String? = null,

    @field:SerializedName("qrcode")
    val qrcode : String? = null,

)
