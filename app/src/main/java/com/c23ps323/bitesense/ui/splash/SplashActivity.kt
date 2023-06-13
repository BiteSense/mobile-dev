package com.c23ps323.bitesense.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.c23ps323.bitesense.MainActivity
import com.c23ps323.bitesense.R
import com.c23ps323.bitesense.ui.auth.AuthActivity
import com.c23ps323.bitesense.utils.UserPreference

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private var cookie: String? = null
    private var userPreference: UserPreference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        userPreference = UserPreference(this)
        cookie = userPreference!!.getUserCookie()

        hideSystemUI()

        if (cookie != null && cookie != "") {
            Handler(Looper.getMainLooper()).postDelayed({
                val homeIntent = Intent(this, MainActivity::class.java)
                startActivity(homeIntent)
                finish()
            }, 3000)
        } else {
            Handler(Looper.getMainLooper()).postDelayed({
                val authIntent = Intent(this, AuthActivity::class.java)
                startActivity(authIntent)
                finish()
            }, 3000)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        userPreference = null
        cookie = null
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