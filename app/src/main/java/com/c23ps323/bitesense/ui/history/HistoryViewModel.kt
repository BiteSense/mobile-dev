package com.c23ps323.bitesense.ui.history

import androidx.lifecycle.ViewModel
import com.c23ps323.bitesense.data.Repository

class HistoryViewModel(repository: Repository) : ViewModel() {
    val getHistoryProducts = repository.getAllProducts()
}