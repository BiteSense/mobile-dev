package com.c23ps323.bitesense.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.c23ps323.bitesense.data.local.entity.ProductEntity
import com.c23ps323.bitesense.data.local.room.ProductDao
import com.c23ps323.bitesense.data.remote.response.ProductResponse
import com.c23ps323.bitesense.data.remote.response.UserResponse
import com.c23ps323.bitesense.data.remote.retrofit.ApiService

class Repository private constructor(
    private val apiService: ApiService,
    private val productDao: ProductDao
) {

    fun getUserProfile(): LiveData<Result<UserResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getUserProfile()
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getAllProducts(): LiveData<Result<List<ProductEntity>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getAllProduct()
            val products = response.data
            val productList = products?.map {
                val isFavorite = productDao.isFavoriteProduct(it?.namaProduk!!)
                ProductEntity(
                    it.idProduk.toString(),
                    it.namaProduk,
                    it.fotoProduk!!,
                    it.tagProduk.toString(),
                    it.deskripsiProduk.toString(),
                    it.komposisiProduk.toString(),
                    it.alert!!,
                    isFavorite
                )
            }
            productDao.deleteAll()
            productDao.insertProducts(productList!!)
        } catch (e: Exception) {
            emit(Result.Error(e.toString()))
        }
        val localData: LiveData<Result<List<ProductEntity>>> = productDao.getProducts().map { Result.Success(it) }
        emitSource(localData)
    }

    fun getFavoriteProducts(): LiveData<List<ProductEntity>> {
        return productDao.getFavoriteProducts()
    }

    suspend fun setFavoriteProduct(product: ProductEntity, favoriteState: Boolean) {
        product.isFavorite = favoriteState
        productDao.updateProduct(product)
    }

    fun getLastScannedProducts(): LiveData<Result<List<ProductEntity>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getProductLastScan()
            val products = response.data
            val productList = products?.map {
                val isFavorite = productDao.isFavoriteProduct(it?.namaProduk!!)
                ProductEntity(
                    it.idProduk.toString(),
                    it.namaProduk,
                    it.fotoProduk!!,
                    it.tagProduk.toString(),
                    it.deskripsiProduk.toString(),
                    it.komposisiProduk.toString(),
                    it.alert!!,
                    isFavorite
                )
            }
            productDao.deleteAll()
            productDao.insertProducts(productList!!)
        } catch (e: Exception) {
            emit(Result.Error(e.toString()))
        }
        val localData: LiveData<Result<List<ProductEntity>>> = productDao.getProducts().map { Result.Success(it) }
        emitSource(localData)
    }

    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            apiService: ApiService,
            productDao: ProductDao
        ): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(apiService, productDao)
            }.also { instance = it }
    }
}