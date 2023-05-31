package com.c23ps323.bitesense.ui.favorite

import androidx.lifecycle.ViewModel
import com.c23ps323.bitesense.data.Repository

class FavoriteViewModel(repository: Repository) : ViewModel() {
    val getFavoriteProducts = repository.getFavoriteProducts()
}