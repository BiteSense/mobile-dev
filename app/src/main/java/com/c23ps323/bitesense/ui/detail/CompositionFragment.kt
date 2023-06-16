package com.c23ps323.bitesense.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.c23ps323.bitesense.data.Result
import com.c23ps323.bitesense.data.remote.response.DetailProdukResponse
import com.c23ps323.bitesense.databinding.FragmentCompositionBinding
import com.c23ps323.bitesense.utils.ViewModelFactory

class CompositionFragment : Fragment() {
    private var _binding: FragmentCompositionBinding? = null
    private val binding get() = _binding!!
    private val detailViewModel: DetailViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }
    private var id: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCompositionBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = arguments?.getString(DescriptionFragment.EXTRA_ID)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getDetailData(id!!)
    }

    private fun getDetailData(id: String) {
        detailViewModel.getDetailProduct(id).observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> showLoading(true)
                    is Result.Success -> successGetDetailProduct(result.data)
                    is Result.Error -> showLoading(false)
                }
            }
        }
    }

    private fun successGetDetailProduct(result: DetailProdukResponse) {
        showLoading(false)
        binding.tvComposition.text = result.data?.komposisiProduk
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val EXTRA_ID = "extra_id"
    }
}