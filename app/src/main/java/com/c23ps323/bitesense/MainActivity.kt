package com.c23ps323.bitesense

import android.content.Intent

import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

import androidx.fragment.app.Fragment
import com.c23ps323.bitesense.databinding.ActivityMainBinding
import com.c23ps323.bitesense.ui.camera.CameraActivity
import com.c23ps323.bitesense.ui.favorite.FavoriteFragment
import com.c23ps323.bitesense.ui.generateqr.InputGenerateQRActivity
import com.c23ps323.bitesense.ui.history.HistoryFragment
import com.c23ps323.bitesense.ui.home.HomeFragment
import com.c23ps323.bitesense.ui.profile.ProfileFragment

import com.c23ps323.bitesense.ui.scanqr.ScannerQRActivity

import com.google.android.material.navigation.NavigationBarView


class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!


    private var isExpanded = false

    private val fromBottomFabAnim: Animation by lazy {
        AnimationUtils.loadAnimation(this, R.anim.from_button_fab)
    }
    private val toBottomFabAnim: Animation by lazy {
        AnimationUtils.loadAnimation(this, R.anim.to_button_fab)
    }
    private val rotateClockWiseFabAnim: Animation by lazy {
        AnimationUtils.loadAnimation(this, R.anim.rotate_clock_wise)
    }
    private val rotateAntiClockWiseFabAnim: Animation by lazy {
        AnimationUtils.loadAnimation(this, R.anim.rotate_anti_clock_wise)
    }
    private val fromBottomBgAnim: Animation by lazy {
        AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim)
    }
    private val toBottomBgAnim: Animation by lazy {
        AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        setupBottomNavigationBar()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.frame_container, HomeFragment(), HomeFragment::class.java.simpleName)
                .commit()
        }

        binding.bottomNavigationBar.setOnItemSelectedListener(this)
        binding.fab.setOnClickListener {

            if (isExpanded) {
                shrinkFab()
            } else {
                expandFab()
            }

        }

        binding.btnScanProduct.setOnClickListener {
            startCameraX()
        }
        binding.btnScanQrCode.setOnClickListener {
            Intent(this, ScannerQRActivity::class.java).also {
                startActivity(it)
            }
        }
        binding.btnGenereteQr.setOnClickListener {

            Intent(this,InputGenerateQRActivity::class.java).also {
                startActivity(it)
            }

        }

    }

    private fun expandFab() {

        binding.fab.startAnimation(rotateClockWiseFabAnim)
        binding.transparentBg.startAnimation(fromBottomBgAnim)
        binding.btnScanProduct.startAnimation(fromBottomFabAnim)
        binding.btnScanQrCode.startAnimation(fromBottomFabAnim)
        binding.btnGenereteQr.startAnimation(fromBottomFabAnim)

        isExpanded = !isExpanded

    }

    private fun shrinkFab() {

        binding.fab.startAnimation(rotateAntiClockWiseFabAnim)
        binding.transparentBg.startAnimation(toBottomBgAnim)
        binding.btnScanProduct.startAnimation(toBottomFabAnim)
        binding.btnScanQrCode.startAnimation(toBottomFabAnim)
        binding.btnGenereteQr.startAnimation(toBottomFabAnim)

        isExpanded = !isExpanded


    }

    private fun startCameraX() {
        val intent = Intent(this, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupBottomNavigationBar() {
        binding.apply {
            bottomNavigationBar.background = null
            bottomNavigationBar.menu.getItem(2).isEnabled = false
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val tempFragment: Fragment = when (item.itemId) {
            R.id.mHome -> HomeFragment()
            R.id.mFavorite -> FavoriteFragment()
            R.id.mHistory -> HistoryFragment()
            R.id.mProfile -> ProfileFragment()
            else -> HomeFragment()
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_container, tempFragment)
            .commit()
        return true
    }

    companion object {
        const val EXTRA_TOKEN = "extra_token"
    }
}