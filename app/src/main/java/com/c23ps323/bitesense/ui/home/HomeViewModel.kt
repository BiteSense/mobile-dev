package com.c23ps323.bitesense.ui.home

import androidx.lifecycle.ViewModel
import com.c23ps323.bitesense.data.Repository

class HomeViewModel(repository: Repository) : ViewModel() {
    val getLastScannedProducts = repository.getLastScannedProducts()
}