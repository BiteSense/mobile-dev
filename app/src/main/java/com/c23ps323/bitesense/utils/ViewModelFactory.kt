package com.c23ps323.bitesense.utils

import android.content.Context
import android.os.Build.VERSION_CODES.S
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.c23ps323.bitesense.data.Repository
import com.c23ps323.bitesense.di.Injection
import com.c23ps323.bitesense.ui.auth.login.LoginViewModel
import com.c23ps323.bitesense.ui.auth.register.RegisterViewModel
import com.c23ps323.bitesense.ui.detailqr.DetailQrViewModel

import com.c23ps323.bitesense.ui.editProfile.EditProfileViewModel

import com.c23ps323.bitesense.ui.favorite.FavoriteViewModel
import com.c23ps323.bitesense.ui.generateqr.InputGenerateQrViewModel
import com.c23ps323.bitesense.ui.history.HistoryViewModel
import com.c23ps323.bitesense.ui.home.HomeViewModel
import com.c23ps323.bitesense.ui.preview.PreviewViewModel
import com.c23ps323.bitesense.ui.profile.ProfileViewModel
import com.c23ps323.bitesense.ui.splash.SplashActivity
import com.c23ps323.bitesense.ui.splash.SplashViewModel



val Context.dataStore: DataStore<androidx.datastore.preferences.core.Preferences> by preferencesDataStore(name = "users")

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
                RegisterViewModel(repository) as T
            }
            modelClass.isAssignableFrom(SplashViewModel::class.java) ->{
                SplashViewModel(repository) as T
            }
            modelClass.isAssignableFrom(InputGenerateQrViewModel::class.java) ->{
                InputGenerateQrViewModel(repository) as T
            }


            modelClass.isAssignableFrom(PreviewViewModel::class.java) -> {
                PreviewViewModel(repository) as T
            }
            modelClass.isAssignableFrom(EditProfileViewModel::class.java) -> {
                EditProfileViewModel(repository) as T
            }
            modelClass.isAssignableFrom(DetailQrViewModel::class.java) ->{
                DetailQrViewModel(repository)  as T
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