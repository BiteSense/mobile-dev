package com.c23ps323.bitesense.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.c23ps323.bitesense.data.local.entity.ProductEntity
import com.c23ps323.bitesense.data.local.room.ProductDao
import com.c23ps323.bitesense.data.remote.response.EditProfileResponse
import com.c23ps323.bitesense.data.remote.response.HealthConditionResponse
import com.c23ps323.bitesense.data.remote.response.LoginResponse
import com.c23ps323.bitesense.data.remote.response.UploadProductResponse
import com.c23ps323.bitesense.data.remote.response.UserResponse
import com.c23ps323.bitesense.data.remote.retrofit.ApiService
import com.c23ps323.bitesense.utils.UserPreference
import com.google.gson.JsonElement
import okhttp3.MultipartBody

class Repository private constructor(
    private val apiService: ApiService,
    private val productDao: ProductDao,
    private val userPreference: UserPreference
) {
    fun updatePreference(preference: JsonElement): LiveData<Result<UploadProductResponse>> =
        liveData {
            emit(Result.Loading)
            try {
                val response = apiService.updatePreference(preference)
                emit(Result.Success(response))
            } catch (e: Exception) {
                emit(Result.Error(e.message.toString()))
            }
        }

    fun register(
        username: String, email: String, password: String, repassword: String
    ): LiveData<Result<UploadProductResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.register(username, email, password, repassword)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun login(email: String, password: String): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.login(email, password)
            val headerResponse = response.headers()
            val headerMapList: Map<String, List<String>> = headerResponse.toMultimap()
            val allCookies: List<String> = headerMapList["set-cookie"]!!
            var cookieVal = ""
            for (i in 1 until allCookies.size) {
                val newCookie = allCookies[i].replace("\\s*Path=/\\s*".toRegex(), "")
                cookieVal += newCookie
            }
            userPreference.saveUserCookie(cookieVal)
            emit(Result.Success(response.body()!!))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getScannedProducts(): LiveData<Result<List<ProductEntity>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getScannedProducts()
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
            emit(Result.Success(productList))
        } catch (e: Exception) {
            emit(Result.Error(e.toString()))
        }
        val localData: LiveData<Result<List<ProductEntity>>> =
            productDao.getScannedProducts().map { Result.Success(it) }
        emitSource(localData)
    }

    fun editTelepon(telepon: String): LiveData<Result<EditProfileResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.editTelepon(telepon)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun editEmail(email: String): LiveData<Result<EditProfileResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.editEmail(email)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun editUsername(username: String): LiveData<Result<EditProfileResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.editUsername(username)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun uploadProduct(
        productImage: MultipartBody.Part
    ): LiveData<Result<UploadProductResponse>> = liveData {
        emit(Result.Loading)
        try {
            val tempCookie = userPreference.getUserCookie()
            val response = apiService.uploadProduct(productImage)
            val responseCookie: String = response.headers()["set-cookie"]!!
            val newCookie = responseCookie.replace("\\s*Path=/\\s*".toRegex(), "")
            userPreference.removeUserCookie()
            userPreference.saveUserCookie("$tempCookie $newCookie")
            emit(Result.Success(response.body()!!))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

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

    suspend fun deleteAllData() = productDao.deleteTable()

    fun getFavoriteProducts(): LiveData<List<ProductEntity>> = productDao.getFavoriteProducts()

    suspend fun setFavoriteProduct(product: ProductEntity, favoriteState: Boolean) {
        product.isFavorite = favoriteState
        productDao.updateProduct(product)
    }

    fun getAllProducts(): LiveData<Result<List<ProductEntity>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getAllProduct()
            val products = response.data
            val productList = products?.map {
                val isFavorite = productDao.isFavoriteProduct(it?.idProduk!!.toString())
                ProductEntity(
                    it.idProduk.toString(),
                    it.namaProduk!!,
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
        val localData: LiveData<Result<List<ProductEntity>>> =
            productDao.getProducts().map { Result.Success(it) }
        emitSource(localData)
    }

    fun getLastScannedProducts(): LiveData<Result<List<ProductEntity>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getAllProduct()
            val products = response.data
            val productList = products?.map {
                val isFavorite = productDao.isFavoriteProduct(it?.idProduk!!.toString())
                ProductEntity(
                    it.idProduk.toString(),
                    it.namaProduk!!,
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
        val localData: LiveData<Result<List<ProductEntity>>> =
            productDao.getLastProducts().map { Result.Success(it) }
        emitSource(localData)
    }

    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            apiService: ApiService,
            productDao: ProductDao,
            userPreference: UserPreference,
        ): Repository = instance ?: synchronized(this) {
            instance ?: Repository(apiService, productDao, userPreference)
        }.also { instance = it }
    }
}