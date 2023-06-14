package com.c23ps323.bitesense.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.c23ps323.bitesense.adapter.ProductAdapter
import com.c23ps323.bitesense.data.local.entity.ProductEntity
import com.c23ps323.bitesense.databinding.FragmentFavoriteBinding
import com.c23ps323.bitesense.ui.detail.DetailActivity
import com.c23ps323.bitesense.utils.ViewModelFactory

class FavoriteFragment : Fragment(), ProductAdapter.OnItemClickListener {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private val favoriteViewModel: FavoriteViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoriteViewModel.getFavoriteProducts.observe(viewLifecycleOwner) { favoriteProducts ->
            setupRecyclerView(favoriteProducts)
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

    private fun setupRecyclerView(products: List<ProductEntity>) {
        val productAdapter = ProductAdapter(this) { product ->
            if (product.isFavorite) {
                favoriteViewModel.saveProduct(product)
            } else {
                favoriteViewModel.deleteProduct(product)
            }
        }
        productAdapter.submitList(products)
        binding.rvFavoriteItems.adapter = productAdapter
        binding.apply {
            rvFavoriteItems.layoutManager = LinearLayoutManager(requireContext())
            rvFavoriteItems.setHasFixedSize(true)
        }
    }
}