package com.c23ps323.bitesense.data.remote.response

import com.google.gson.annotations.SerializedName

data class DetailProdukResponse(

	@field:SerializedName("data")
	val data: DataProduk? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("statusCode")
	val statusCode: Int? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class DataProduk(

	@field:SerializedName("alergen_produk")
	val alergenProduk: Any? = null,

	@field:SerializedName("id_produk")
	val idProduk: Int? = null,

	@field:SerializedName("nama_produk")
	val namaProduk: String? = null,

	@field:SerializedName("id_detail")
	val idDetail: Int? = null,

	@field:SerializedName("alert")
	val alert: Int? = null,

	@field:SerializedName("tag_produk")
	val tagProduk: Any? = null,

	@field:SerializedName("komposisi_produk")
	val komposisiProduk: String? = null,

	@field:SerializedName("id_user")
	val idUser: Int? = null,

	@field:SerializedName("deskripsi_produk")
	val deskripsiProduk: String? = null,

	@field:SerializedName("foto_produk")
	val fotoProduk: String? = null,

	@field:SerializedName("favorite")
	val favorite: Int? = null
)
