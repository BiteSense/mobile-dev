package com.c23ps323.bitesense.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.c23ps323.bitesense.R

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        supportActionBar?.hide()

    }

}