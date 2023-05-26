package com.c23ps323.bitesense

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationBarView
import com.c23ps323.bitesense.databinding.ActivityMainBinding
import com.c23ps323.bitesense.ui.favorite.FavoriteFragment
import com.c23ps323.bitesense.ui.history.HistoryFragment
import com.c23ps323.bitesense.ui.home.HomeFragment
import com.c23ps323.bitesense.ui.camera.CameraActivity
import com.c23ps323.bitesense.ui.profile.ProfileFragment
import java.io.File

class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
//    private var getFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        setupBottomNavigationBar()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.frame_container, HomeFragment(), HomeFragment::class.java.simpleName)
                .commit()
        }

        binding.bottomNavigationBar.setOnItemSelectedListener(this)
        binding.fab.setOnClickListener {
            startCameraX()
        }
    }

    private fun startCameraX() {
        val intent = Intent(this, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.data?.getSerializableExtra("picture", File::class.java)
            } else {
                @Suppress("DEPRECATION")
                it.data?.getSerializableExtra("picture")
            } as? File

            // TODO: SET TO PREVIEW PAGE

//            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean

//            myFile?.let { file ->
//                rotateFile(file, isBackCamera)
//                getFile = file
//                binding.ivPreview.setImageBitmap(BitmapFactory.decodeFile(file.path))
//            }
        }
    }

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
        val tempFragment: Fragment = when(item.itemId) {
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
        const val CAMERA_X_RESULT = 200
    }
}