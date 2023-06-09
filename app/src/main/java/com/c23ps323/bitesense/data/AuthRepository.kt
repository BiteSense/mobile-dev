package com.c23ps323.bitesense.data

import com.c23ps323.bitesense.data.local.AuthPreferencesDataSource
import com.c23ps323.bitesense.data.remote.response.LoginResponse
import com.c23ps323.bitesense.data.remote.response.RegisterResponse
import com.c23ps323.bitesense.data.remote.retrofit.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AuthRepository constructor(
    private val apiService: ApiService,
    private val preferencesDataSource: AuthPreferencesDataSource
) {

    /**
     * Handle login operation for the users by calling the related API
     *
     * @param email User's email
     * @param password User's password
     * @return Flow
     */
    suspend fun userLogin(email: String, password: String): Flow<Result<LoginResponse>> = flow {
        try {
            val response = apiService.userLogin(email, password)
            emit(Result.Success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

    /**
     * Handle registration process for the users by calling the related API
     *
     * @param name User's full name
     * @param email User's email
     * @param password User's password
     * @return Flow
     */
    suspend fun userRegister(
        name: String,
        email: String,
        password: String
    ): Flow<Result<RegisterResponse>> = flow {
        try {
            val response = apiService.userRegister(name, email, password)
            emit(Result.Success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

    /**
     * Save user's authentication token to the preferences
     *
     * @param token User's authentication token
     */
    suspend fun saveAuthToken(token: String) {
        preferencesDataSource.saveAuthToken(token)
    }

    /**
     * Get the user's authentication token from preferences
     *
     * @return Flow
     */
    fun getAuthToken(): Flow<String?> = preferencesDataSource.getAuthToken()
}