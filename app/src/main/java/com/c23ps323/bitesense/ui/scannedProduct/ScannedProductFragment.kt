package com.c23ps323.bitesense.ui.scannedProduct

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.c23ps323.bitesense.adapter.ProductAdapter
import com.c23ps323.bitesense.data.Result
import com.c23ps323.bitesense.data.response.DataItem
import com.c23ps323.bitesense.data.response.ProductResponse
import com.c23ps323.bitesense.databinding.FragmentScannedProductBinding
import com.c23ps323.bitesense.entities.Product
import com.c23ps323.bitesense.entities.ProductData
import com.c23ps323.bitesense.ui.detail.DetailActivity
import com.c23ps323.bitesense.ui.home.HomeViewModel
import com.c23ps323.bitesense.utils.ViewModelFactory

class ScannedProductFragment : Fragment(), ProductAdapter.OnItemClickListener {
    private var _binding: FragmentScannedProductBinding? = null
    private val binding get() = _binding!!
    private var list: ArrayList<Product> = arrayListOf()
    private val scannedProductViewModel: HomeViewModel by viewModels {
        ViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScannedProductBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showSystemUI()

        list.addAll(ProductData.favoriteListData)

        scannedProductViewModel.getLastScannedProducts.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when(result) {
                    is Result.Loading -> showLoading(true)
                    is Result.Success -> {
                        showLoading(false)
                        setupRecyclerView(result.data)
                    }
                    is Result.Error -> {
                        showLoading(false)
                        Toast.makeText(
                            requireContext(),
                            result.error,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun showSystemUI() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            requireActivity().window.insetsController?.show(WindowInsets.Type.statusBars())
        } else {
            requireActivity().window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onItemClick(id: String) {
        val intent = Intent(requireContext(), DetailActivity::class.java)
        startActivity(intent)
    }

    private fun showLoading(show: Boolean) {
        if (show) {
            binding.apply {
                progressBar.visibility = View.VISIBLE
                linearLayout.visibility = View.GONE
            }
        } else {
            binding.apply {
                progressBar.visibility = View.GONE
                linearLayout.visibility = View.VISIBLE
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun setupRecyclerView(product: ProductResponse) {
        binding.rvScannedProducts.adapter = ProductAdapter(this,
            product.data as List<DataItem>
        )
        binding.apply {
            rvScannedProducts.layoutManager = LinearLayoutManager(requireContext())
            rvScannedProducts.setHasFixedSize(true)
        }
    }
}