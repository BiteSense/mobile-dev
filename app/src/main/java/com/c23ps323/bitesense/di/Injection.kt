package com.c23ps323.bitesense.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.c23ps323.bitesense.data.Repository
import com.c23ps323.bitesense.data.local.AuthPreferencesDataSource
import com.c23ps323.bitesense.data.local.room.ProductDatabase
import com.c23ps323.bitesense.data.remote.retrofit.ApiConfig


object Injection {
    fun provideRepository(context: Context,dataStore : DataStore<Preferences>): Repository {
        val apiService = ApiConfig.getApiService(context)
        val database = ProductDatabase.getInstance(context)
        val dao = database.productDao()
        val preferences = AuthPreferencesDataSource.getInstance(dataStore)
        return Repository.getInstance(apiService, dao,preferences)
    }



}