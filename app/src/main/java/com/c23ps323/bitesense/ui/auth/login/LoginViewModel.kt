package com.c23ps323.bitesense.ui.auth.login

import androidx.lifecycle.ViewModel
import com.c23ps323.bitesense.data.Repository

class LoginViewModel(private val repository: Repository) : ViewModel() {
    fun login(email: String, password: String) = repository.login(email, password)
}