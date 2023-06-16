package com.c23ps323.bitesense.ui.detailqr

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.c23ps323.bitesense.R
import com.c23ps323.bitesense.databinding.ActivityDetailQrBinding
import com.c23ps323.bitesense.utils.ViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DetailQrActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailQrBinding
    private var scanJob: Job = Job()

    private val detailQrViewModel: DetailQrViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailQrBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val id_produk = intent.getStringExtra(EXTRA_ID_PRODUCT)

        handleScanQr(id_produk!!.toInt())


    }

    @Suppress("DEPRECATION")
    private fun handleScanQr(id_product: Int) {

        lifecycleScope.launchWhenResumed {
            if (scanJob.isActive) scanJob.cancel()

            scanJob = launch {
                detailQrViewModel.scanQR(666063169).collect { result ->
                    result.onSuccess { dataProduk ->
                        dataProduk.data.let {
                            binding.tvProductName.text = it[0].nama_produk
                            binding.tvComposition.text = it[0].komposisi_produk
                            binding.tvExp.text = it[0].expired
                            binding.tvTglProduksi.text = it[0].tgl_produksi
                        }
                    }
                    result.onFailure {
                        Snackbar.make(
                            binding.root,
                            getString(R.string.scanqr_error_message),
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

    }

    companion object {
        const val EXTRA_ID_PRODUCT = "EXTRA_ID_PRODUCT"
    }
}