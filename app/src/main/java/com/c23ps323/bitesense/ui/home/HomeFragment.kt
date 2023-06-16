package com.c23ps323.bitesense.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.c23ps323.bitesense.R
import com.c23ps323.bitesense.adapter.ProductAdapter
import com.c23ps323.bitesense.data.Result
import com.c23ps323.bitesense.data.local.entity.ProductEntity
import com.c23ps323.bitesense.databinding.FragmentHomeBinding
import com.c23ps323.bitesense.ui.detail.DetailActivity
import com.c23ps323.bitesense.utils.ViewModelFactory

class HomeFragment : Fragment(), ProductAdapter.OnItemClickListener {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNotification.setOnClickListener {
            Toast.makeText(
                requireContext(),
                getString(R.string.under_development),
                Toast.LENGTH_SHORT
            ).show()
        }

        setupUserData()
        setupProductData()
    }

    private fun setupProductData() {
        homeViewModel.getLastScannedProducts.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> showLoading(true)
                    is Result.Success -> {
                        showLoading(false)
                        setupRecyclerView(result.data)
                    }
                    is Result.Error -> {
                        showLoading(false)
                        Toast.makeText(
                            context,
                            getString(R.string.general_error),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun setupUserData() {
        homeViewModel.user.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> showLoading(true)
                    is Result.Success -> {
                        showLoading(false)
                        Glide.with(requireContext())
                            .load(result.data.data?.result?.fotoUser)
                            .into(binding.ivProfile)
                        binding.tvName.text =
                            getString(R.string.greet_user, result.data.data?.result?.username)
                    }
                    is Result.Error -> {
                        showLoading(false)
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.general_error),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onItemClick(id: String) {
        val intent = Intent(requireContext(), DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_ID, id)
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

    private fun setupRecyclerView(products: List<ProductEntity>) {
        val linearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        val productAdapter = ProductAdapter(this) { product ->
            if (product.isFavorite) {
                homeViewModel.saveProduct(product)
            } else {
                homeViewModel.deleteProduct(product)
            }
        }
        productAdapter.submitList(products)
        binding.apply {
            rvLastScannedItems.layoutManager = linearLayoutManager
            rvLastScannedItems.setHasFixedSize(true)
            rvLastScannedItems.adapter = productAdapter
        }
    }
}