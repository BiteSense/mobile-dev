package com.c23ps323.bitesense.ui.auth.register

import androidx.lifecycle.ViewModel
import com.c23ps323.bitesense.data.Repository

class RegisterViewModel constructor(
    private val repository: Repository
) : ViewModel() {
    suspend fun userRegister(name: String, email: String, password: String, repassword: String) =
        repository.userRegister(name, email, password, repassword)
}