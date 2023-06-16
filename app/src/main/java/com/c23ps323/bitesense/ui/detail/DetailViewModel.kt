package com.c23ps323.bitesense.ui.detail

import androidx.lifecycle.ViewModel
import com.c23ps323.bitesense.data.Repository

class DetailViewModel(private val repository: Repository) : ViewModel() {
    fun getDetailProduct(id: String) = repository.getDetailProduct(id)
}