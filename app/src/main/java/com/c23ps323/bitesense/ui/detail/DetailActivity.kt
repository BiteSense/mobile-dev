package com.c23ps323.bitesense.ui.detail

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.c23ps323.bitesense.R
import com.c23ps323.bitesense.adapter.SectionPagerAdapter
import com.c23ps323.bitesense.data.Result
import com.c23ps323.bitesense.data.remote.response.DetailProdukResponse
import com.c23ps323.bitesense.databinding.ActivityDetailBinding
import com.c23ps323.bitesense.utils.ViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!
    private val detailViewModel: DetailViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }
    private var id: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getIdFromExtra()
        supportActionBar?.hide()

        getDetailData(id!!)
        val sectionPagerAdapter = SectionPagerAdapter(this)
        sectionPagerAdapter.setId(id ?: "")
        binding.viewPager.adapter = sectionPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        binding.btnBack.setOnClickListener {
            finish()
        }

        setupAnimation()
    }

    private fun getIdFromExtra() {
        val extra = intent.extras
        if (extra != null) {
            id = extra.getString(EXTRA_ID)
        }
    }

    private fun getDetailData(id: String) {
        detailViewModel.getDetailProduct(id).observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> showLoading(true)
                    is Result.Success -> successGetDetailProduct(result.data)
                    is Result.Error -> showError(getString(R.string.fail_get_detail))
                }
            }
        }
    }

    private fun showError(msg: String) {
        showLoading(false)
        Toast.makeText(
            this,
            msg,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun successGetDetailProduct(result: DetailProdukResponse) {
        showLoading(false)
        val alert = result.data?.alert
        Glide.with(this)
            .load(result.data?.fotoProduk)
            .into(binding.ivProduct)
        binding.warningContainer.background = when (alert) {
            0 -> ContextCompat.getDrawable(this, R.color.safeColor)
            1 -> ContextCompat.getDrawable(this, R.color.warningColor)
            2 -> ContextCompat.getDrawable(this, R.color.dangerColor)
            else -> ContextCompat.getDrawable(this, R.color.warningColor)
        }
        binding.apply {
            tvProductName.text = result.data?.namaProduk
            tvWarning.text = (when (alert) {
                0 -> getString(R.string.safe)
                1 -> getString(R.string.warning)
                2 -> getString(R.string.danger)
                else -> getString(R.string.warning)
            }).toString()
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.apply {
                progressBar.visibility = View.VISIBLE
                linearLayout2.visibility = View.GONE
                warningContainer.visibility = View.GONE
            }
        } else {
            binding.apply {
                progressBar.visibility = View.GONE
                linearLayout2.visibility = View.VISIBLE
                warningContainer.visibility = View.VISIBLE
            }
        }
    }

    private fun setupAnimation() {
        val container =
            ObjectAnimator.ofFloat(binding.warningContainer, View.TRANSLATION_Y, 100f, 0f)
        container.duration = 1000
        container.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
        const val EXTRA_ID = "extra_id"
    }
}