package com.c23ps323.bitesense.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.c23ps323.bitesense.data.Repository
import com.c23ps323.bitesense.di.Injection
import com.c23ps323.bitesense.ui.auth.login.LoginViewModel
import com.c23ps323.bitesense.ui.auth.register.RegisterViewModel
import com.c23ps323.bitesense.ui.favorite.FavoriteViewModel
import com.c23ps323.bitesense.ui.history.HistoryViewModel
import com.c23ps323.bitesense.ui.home.HomeViewModel
import com.c23ps323.bitesense.ui.profile.ProfileViewModel


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "users")

class ViewModelFactory(private val repository: Repository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(repository) as T
            }
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(repository) as T
            }
            modelClass.isAssignableFrom(HistoryViewModel::class.java) -> {
                HistoryViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(repository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) ->{
                LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) ->{
                LoginViewModel(repository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
    companion object {
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory {
            return INSTANCE ?: synchronized(this) {
                ViewModelFactory(Injection.provideRepository(context, context.dataStore)).also {
                    INSTANCE = it
                }
            }
        }
    }
}