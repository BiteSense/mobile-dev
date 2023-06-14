package com.c23ps323.bitesense.ui.generateqr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.c23ps323.bitesense.R

class GenerateQRActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generate_qractivity)
        supportActionBar?.hide()
    }
    companion object {
        const val EXTRA_DATA = "extra_DATA"
    }
}