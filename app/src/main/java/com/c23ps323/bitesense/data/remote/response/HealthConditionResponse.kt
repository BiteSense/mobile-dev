package com.c23ps323.bitesense.data.remote.response

import com.google.gson.annotations.SerializedName

data class HealthConditionResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("statusCode")
	val statusCode: Int? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class Data(

	@field:SerializedName("dataKondisi")
	val dataKondisi: List<DataKondisiItem?>? = null,

	@field:SerializedName("dataFood")
	val dataFood: List<DataFoodItem?>? = null,

	@field:SerializedName("dataPenyakit")
	val dataPenyakit: List<DataPenyakitItem?>? = null
)

data class DataFoodItem(

	@field:SerializedName("name_food")
	val nameFood: String? = null,

	@field:SerializedName("triger_food")
	val trigerFood: String? = null
)

data class DataPenyakitItem(

	@field:SerializedName("nama_penyakit")
	val namaPenyakit: String? = null,

	@field:SerializedName("triger_penyakit")
	val trigerPenyakit: String? = null
)

data class DataKondisiItem(

	@field:SerializedName("triger_condition")
	val trigerCondition: String? = null,

	@field:SerializedName("name_condition")
	val nameCondition: String? = null
)
