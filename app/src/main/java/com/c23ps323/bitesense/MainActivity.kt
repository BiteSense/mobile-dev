package com.c23ps323.bitesense

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationBarView
import com.c23ps323.bitesense.databinding.ActivityMainBinding
import com.c23ps323.bitesense.ui.favorite.FavoriteFragment
import com.c23ps323.bitesense.ui.history.HistoryFragment
import com.c23ps323.bitesense.ui.home.HomeFragment
import com.c23ps323.bitesense.ui.profile.ProfileFragment

class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

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
            Toast.makeText(
                this,
                "Under Development",
                Toast.LENGTH_SHORT
            ).show()
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
}