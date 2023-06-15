package com.c23ps323.bitesense.ui.auth.register

import androidx.lifecycle.ViewModel
import com.c23ps323.bitesense.data.Repository

class RegisterViewModel(private val repository: Repository) : ViewModel() {
    fun register(username: String, email: String, password: String, repassword: String) =
        repository.register(username, email, password, repassword)
}