package com.c23ps323.bitesense.ui.generateqr

import androidx.lifecycle.ViewModel
import com.c23ps323.bitesense.data.Repository
import kotlinx.coroutines.flow.Flow

class InputGenerateQrViewModel constructor(
private val repository: Repository
) : ViewModel() {


  suspend fun createQrCode(  nama_produk : String, komposisi_produk : String, expired : String, tgl_produksi : String)= repository.createQrCode(nama_produk,komposisi_produk,expired,tgl_produksi)

  fun getAuthToken(): Flow<String?> = repository.getAuthToken()


}