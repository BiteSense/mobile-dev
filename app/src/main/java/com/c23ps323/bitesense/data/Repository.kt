package com.c23ps323.bitesense.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.c23ps323.bitesense.data.local.AuthPreferencesDataSource
import com.c23ps323.bitesense.data.local.entity.ProductEntity
import com.c23ps323.bitesense.data.local.room.ProductDao
import com.c23ps323.bitesense.data.remote.response.*
import com.c23ps323.bitesense.data.remote.retrofit.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class Repository private constructor(
    private val apiService: ApiService,
    private val productDao: ProductDao,
    private val preferencesDataSource: AuthPreferencesDataSource
) {
    fun getUserHealthCondition(): LiveData<Result<HealthConditionResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getUserHealthCondition()
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

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


    suspend fun userLogin(email: String, password: String): Flow<Result<LoginResponse>> = flow {
        try {
            val response = apiService.userLogin(email, password)
            emit(Result.Success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)


    suspend fun userRegister(
        name: String,
        email: String,
        password: String,
        repassword: String,
    ): Flow<Result<RegisterResponse>> = flow {
        try {
            val response = apiService.userRegister(name, email, password,repassword)
            emit(Result.Success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun saveAuthToken(token: String) {
        preferencesDataSource.saveAuthToken(token)
    }


    fun getAuthToken(): Flow<String?> = preferencesDataSource.getAuthToken()

    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            apiService: ApiService,
            productDao: ProductDao,
            preferencesDataSource: AuthPreferencesDataSource
        ): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(apiService, productDao,preferencesDataSource)
            }.also { instance = it }
    }
}