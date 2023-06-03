package com.c23ps323.bitesense.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("statusCode")
	val statusCode: Int? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class Result(

	@field:SerializedName("foto_user")
	val fotoUser: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("username")
	val username: String? = null,

	@field:SerializedName("no_telepon")
	val noTelepon: Any? = null
)

data class Data(

	@field:SerializedName("result")
	val result: Result? = null
)
