package com.c23ps323.bitesense.ui.preference

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContentProviderCompat.requireContext
import com.c23ps323.bitesense.MainActivity
import com.c23ps323.bitesense.R
import com.c23ps323.bitesense.databinding.ActivityPreferenceBinding
import com.c23ps323.bitesense.ui.detailqr.DetailQrActivity


class PreferenceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPreferenceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreferenceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.btnSubmit.setOnClickListener {
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
            }
        }
    }


}