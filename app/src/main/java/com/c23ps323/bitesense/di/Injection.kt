package com.c23ps323.bitesense.di

import android.content.Context
import com.c23ps323.bitesense.data.Repository
import com.c23ps323.bitesense.data.local.room.ProductDatabase
import com.c23ps323.bitesense.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): Repository {
        val apiService = ApiConfig.getApiService(context)
        val database = ProductDatabase.getInstance(context)
        val dao = database.productDao()
        return Repository.getInstance(apiService, dao)
    }
}