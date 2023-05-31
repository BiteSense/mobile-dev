package com.c23ps323.bitesense.di

import android.content.Context
import com.c23ps323.bitesense.data.Repository
import com.c23ps323.bitesense.data.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): Repository {
        val apiService = ApiConfig.getApiService(context)
        return Repository.getInstance(apiService)
    }
}