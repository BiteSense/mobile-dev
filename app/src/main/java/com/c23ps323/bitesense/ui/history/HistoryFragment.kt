package com.c23ps323.bitesense.ui.history

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.c23ps323.bitesense.adapter.ProductAdapter
import com.c23ps323.bitesense.data.Result
import com.c23ps323.bitesense.data.response.DataItem
import com.c23ps323.bitesense.data.response.ProductResponse
import com.c23ps323.bitesense.databinding.FragmentHistoryBinding
import com.c23ps323.bitesense.entities.Product
import com.c23ps323.bitesense.entities.ProductData
import com.c23ps323.bitesense.ui.detail.DetailActivity
import com.c23ps323.bitesense.utils.ViewModelFactory

class HistoryFragment : Fragment(), ProductAdapter.OnItemClickListener {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private var list: ArrayList<Product> = arrayListOf()
    private val historyViewModel: HistoryViewModel by viewModels {
        ViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        list.addAll(ProductData.listData)

        historyViewModel.getHistoryProducts.observe(viewLifecycleOwner) { result ->
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
        binding.rvHistoryItems.adapter = ProductAdapter(this,
            product.data as List<DataItem>
        )
        binding.apply {
            rvHistoryItems.layoutManager = LinearLayoutManager(requireContext())
            rvHistoryItems.setHasFixedSize(true)
        }
    }
}