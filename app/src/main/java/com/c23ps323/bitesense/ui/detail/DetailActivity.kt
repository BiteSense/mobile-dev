package com.c23ps323.bitesense.ui.detail

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.c23ps323.bitesense.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        setupAnimation()
    }

    private fun setupAnimation() {
        val container = ObjectAnimator.ofFloat(binding.warningContainer, View.TRANSLATION_Y, 100f, 0f)
        container.duration = 1000
        container.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}