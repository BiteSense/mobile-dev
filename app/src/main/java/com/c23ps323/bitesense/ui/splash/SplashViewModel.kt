package com.c23ps323.bitesense.ui.splash

import androidx.lifecycle.ViewModel
import com.c23ps323.bitesense.data.Repository
import kotlinx.coroutines.flow.Flow

class SplashViewModel  constructor(private val repository: Repository) :
    ViewModel() {

    fun getAuthToken(): Flow<String?> = repository.getAuthToken()
}