package com.c23ps323.bitesense.ui.preview

import androidx.lifecycle.ViewModel
import com.c23ps323.bitesense.data.Repository
import okhttp3.MultipartBody

class PreviewViewModel(private val repository: Repository) : ViewModel() {
    fun uploadProduct(imageFile: MultipartBody.Part) = repository.uploadProduct(imageFile)
}