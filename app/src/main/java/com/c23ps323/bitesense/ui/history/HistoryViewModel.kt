package com.c23ps323.bitesense.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.c23ps323.bitesense.data.Repository
import com.c23ps323.bitesense.data.local.entity.ProductEntity
import kotlinx.coroutines.launch

class HistoryViewModel(private val repository: Repository) : ViewModel() {
    val getHistoryProducts = repository.getAllProducts()

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