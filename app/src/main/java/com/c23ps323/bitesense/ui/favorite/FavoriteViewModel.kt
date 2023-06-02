package com.c23ps323.bitesense.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.c23ps323.bitesense.data.Repository
import com.c23ps323.bitesense.data.local.entity.ProductEntity
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repository: Repository) : ViewModel() {
    val getFavoriteProducts = repository.getFavoriteProducts()

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
}