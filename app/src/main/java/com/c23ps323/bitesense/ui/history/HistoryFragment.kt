package com.c23ps323.bitesense.ui.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.c23ps323.bitesense.adapter.ProductAdapter
import com.c23ps323.bitesense.databinding.FragmentHistoryBinding
import com.c23ps323.bitesense.entities.Product
import com.c23ps323.bitesense.entities.ProductData

class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private var list: ArrayList<Product> = arrayListOf()

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

        binding.apply {
            rvHistoryItems.adapter = ProductAdapter(list)
            rvHistoryItems.layoutManager = LinearLayoutManager(requireContext())
            rvHistoryItems.setHasFixedSize(true)
        }
    }
}