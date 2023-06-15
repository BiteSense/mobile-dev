package com.c23ps323.bitesense.ui.detailqr

import androidx.lifecycle.ViewModel
import com.c23ps323.bitesense.data.Repository

class DetailQrViewModel (private val repository: Repository) : ViewModel() {
    suspend fun scanQR(id_produk :Int) = repository.scanQR(id_produk)


}