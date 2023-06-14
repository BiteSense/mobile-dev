package com.c23ps323.bitesense.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.c23ps323.bitesense.MainActivity
import com.c23ps323.bitesense.MainActivity.Companion.EXTRA_TOKEN
import com.c23ps323.bitesense.R
import com.c23ps323.bitesense.ui.auth.AuthActivity
import com.c23ps323.bitesense.utils.ViewModelFactory
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {


    private val viewModel: SplashViewModel by viewModels{
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        hideSystemUI()

        determineUserDirection()
    }

    private fun determineUserDirection() {
        lifecycleScope.launchWhenCreated {
            launch {
                viewModel.getAuthToken().collect { token ->
                    if (token.isNullOrEmpty()) {
                        Intent(this@SplashActivity, AuthActivity::class.java).also { intent ->
                            startActivity(intent)
                            finish()
                        }
                    } else {

                        Intent(this@SplashActivity, MainActivity::class.java).also { intent ->
                            intent.putExtra(EXTRA_TOKEN, token)
                            startActivity(intent)
                            finish()
                        }
                    }
                }
            }
        }
    }

    private fun hideSystemUI() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }
}