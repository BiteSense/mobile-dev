package com.c23ps323.bitesense.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.c23ps323.bitesense.di.Injection
import com.c23ps323.bitesense.ui.editProfile.EditProfileViewModel
import com.c23ps323.bitesense.ui.favorite.FavoriteViewModel
import com.c23ps323.bitesense.ui.history.HistoryViewModel
import com.c23ps323.bitesense.ui.home.HomeViewModel
import com.c23ps323.bitesense.ui.preview.PreviewViewModel
import com.c23ps323.bitesense.ui.profile.ProfileViewModel

class ViewModelFactory(private val context: Context) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(Injection.provideRepository(context)) as T
            }
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(Injection.provideRepository(context)) as T
            }
            modelClass.isAssignableFrom(HistoryViewModel::class.java) -> {
                HistoryViewModel(Injection.provideRepository(context)) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(Injection.provideRepository(context)) as T
            }
            modelClass.isAssignableFrom(PreviewViewModel::class.java) -> {
                PreviewViewModel(Injection.provideRepository(context)) as T
            }
            modelClass.isAssignableFrom(EditProfileViewModel::class.java) -> {
                EditProfileViewModel(Injection.provideRepository(context)) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}