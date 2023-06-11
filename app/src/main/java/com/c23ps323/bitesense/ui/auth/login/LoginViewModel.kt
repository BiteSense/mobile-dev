package com.c23ps323.bitesense.ui.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.c23ps323.bitesense.data.Repository
import com.c23ps323.bitesense.data.Result
import com.c23ps323.bitesense.data.remote.response.LoginResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class LoginViewModel constructor(
    private val repository: Repository
) : ViewModel() {

    suspend fun userLogin(email: String, password: String): Flow<Result<LoginResponse>> = repository.userLogin(email, password)



    fun saveAuthToken(token: String) {
        viewModelScope.launch {
            repository.saveAuthToken(token)
        }
    }


}