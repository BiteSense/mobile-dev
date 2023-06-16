package com.c23ps323.bitesense.ui.auth.login

import LoginResponse
import androidx.lifecycle.ViewModel
import com.c23ps323.bitesense.data.Repository
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginViewModel constructor(
    private val repository: Repository
) : ViewModel() {

    suspend fun userLogin(email: String, password: String): Flow<Result<Response<LoginResponse>>> =
        repository.userLogin(email, password)

    fun saveAuthToken(token: String) {
        viewModelScope.launch {
            repository.saveAuthToken(token)
        }
    }
}