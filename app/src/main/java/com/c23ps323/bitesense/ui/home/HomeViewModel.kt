package com.c23ps323.bitesense.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.c23ps323.bitesense.data.Repository
import com.c23ps323.bitesense.data.local.entity.ProductEntity
import com.c23ps323.bitesense.utils.UserPreference
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: Repository) : ViewModel() {
    val getLastScannedProducts = repository.getLastScannedProducts()

    fun saveProduct(product: ProductEntity) {
        viewModelScope.launch {
            repository.setFavoriteProduct(product, true)
        }
    }

    fun deleteProduct(product: ProductEntity) {
        viewModelScope.launch {
            repository.setFavoriteProduct(product, false)
        }
    }

    val user = repository.getUserProfile()
}