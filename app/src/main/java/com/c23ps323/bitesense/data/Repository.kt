package com.c23ps323.bitesense.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.c23ps323.bitesense.data.local.AuthPreferencesDataSource
import com.c23ps323.bitesense.data.local.entity.ProductEntity
import com.c23ps323.bitesense.data.local.room.ProductDao

import com.c23ps323.bitesense.data.Result
import com.c23ps323.bitesense.data.remote.response.*
import com.c23ps323.bitesense.data.remote.retrofit.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

import com.c23ps323.bitesense.data.remote.response.EditProfileResponse
import com.c23ps323.bitesense.data.remote.response.HealthConditionResponse
import com.c23ps323.bitesense.data.remote.response.ProductResponse
import com.c23ps323.bitesense.data.remote.response.UploadProductResponse
import com.c23ps323.bitesense.data.remote.response.UserResponse

import okhttp3.MultipartBody
import retrofit2.Response


class Repository private constructor(
    private val apiService: ApiService,
    private val productDao: ProductDao,
    private val preferencesDataSource: AuthPreferencesDataSource
) {
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
            val response = apiService.uploadProduct(productImage)
            emit(Result.Success(response))
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


    suspend fun userLogin(email: String, password: String): Flow<kotlin.Result<Response<LoginResponse>>> = flow {
        try {

            val response = apiService.userLogin(email, password)
            val header = response.headers().get("set-cookie")
            emit(kotlin.Result.success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(kotlin.Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)


    suspend fun userRegister(
        name: String,
        email: String,
        password: String,
        repassword: String,
    ): Flow<kotlin.Result<RegisterResponse>> = flow {
        try {
            val response = apiService.userRegister(email, password,repassword,name)
            emit(kotlin.Result.success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(kotlin.Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun saveAuthToken(token: String) {
        preferencesDataSource.saveAuthToken(token)
    }

    suspend fun scanQR(
        id_produk : Int
    ):Flow<kotlin.Result<ScanQRResponse>> = flow{
        try {
            val response = apiService.scanQR(id_produk)
            emit(kotlin.Result.success(response))
        }catch (e : Exception){
            e.printStackTrace()
            emit(kotlin.Result.failure(e))
        }

    }

    suspend fun createQrCode(
        nama_produk : String,
        komposisi_produk : String,
        expired : String,
        tgl_produksi : String
    ) : Flow<kotlin.Result<GenerateQrCode>> = flow{
        try {
            val response = apiService.createQrCode(nama_produk,komposisi_produk,expired,tgl_produksi)
            emit(kotlin.Result.success(response))
        }catch (e : Exception){
            e.printStackTrace()
            emit(kotlin.Result.failure(e))
        }

    }.flowOn(Dispatchers.IO)



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