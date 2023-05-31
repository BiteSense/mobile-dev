package com.c23ps323.bitesense.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.c23ps323.bitesense.data.response.ProductResponse
import com.c23ps323.bitesense.data.retrofit.ApiService

class Repository private constructor(private val apiService: ApiService) {

    fun getAllProducts(): LiveData<Result<ProductResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getAllProduct()
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.toString()))
        }
    }

    fun getFavoriteProducts(): LiveData<Result<ProductResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getFavoriteProduct()
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.toString()))
        }
    }

    fun getLastScannedProducts(): LiveData<Result<ProductResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getProductLastScan()
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.toString()))
        }
    }

    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            apiService: ApiService,
        ): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(apiService)
            }.also { instance = it }
    }
}