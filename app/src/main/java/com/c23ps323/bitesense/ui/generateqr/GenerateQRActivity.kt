package com.c23ps323.bitesense.ui.generateqr

import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64


import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.c23ps323.bitesense.R
import com.c23ps323.bitesense.databinding.ActivityGenerateQractivityBinding
import kotlinx.coroutines.CoroutineStart



import kotlin.io.encoding.ExperimentalEncodingApi

class GenerateQRActivity : AppCompatActivity() {

    private lateinit var binding : ActivityGenerateQractivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGenerateQractivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        val qrCodeBase64 = intent.getStringExtra(EXTRA_DATA)
        if (qrCodeBase64 != null) {
            Log.d("QR Code", qrCodeBase64)
        }



        if (qrCodeBase64 != null) {
            binding.ivQrcode.loadFromBase64(qrCodeBase64)
        }


    }

    private fun ImageView.loadFromBase64(encodedImageString : String){
        try {
            val base64Image = encodedImageString.split(",")[1]
            val decodedString = Base64.decode(base64Image,Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(decodedString,0,decodedString.size)
            setImageBitmap(bitmap)
        }catch (e : Exception){
            Toast.makeText(this@GenerateQRActivity,"Error generate QR",Toast.LENGTH_SHORT).show()
        }
    }


//    @RequiresApi(Build.VERSION_CODES.O)
//    private fun convertBase64ToImage(base64String : String, imageView : ImageView) {
//        val decodedBytes = Base64.getDecoder().decode(base64String)
//
//        val bitmap = BitmapFactory.decodeByteArray(decodedBytes,0,decodedBytes.size)
//
//        imageView.setImageBitmap(bitmap)
//    }

    companion object {
        const val EXTRA_DATA = "extra_data"
    }
}