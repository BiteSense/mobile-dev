package com.c23ps323.bitesense.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.c23ps323.bitesense.R
import com.c23ps323.bitesense.data.Result
import com.c23ps323.bitesense.databinding.FragmentProfileBinding
import com.c23ps323.bitesense.utils.UserPreference
import com.c23ps323.bitesense.utils.ViewModelFactory

class ProfileFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val profileViewModel: ProfileViewModel by viewModels {
        ViewModelFactory(requireContext())
    }
    private lateinit var userPreference: UserPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userPreference = UserPreference(requireContext())
        setupButton()

        profileViewModel.getUserProfile.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> showLoading(true)
                    is Result.Success -> {
                        showLoading(false)
                        Glide.with(requireContext())
                            .load(result.data.data?.result?.fotoUser)
                            .into(binding.ivProfile)
                        binding.apply {
                            tvName.text = result.data.data?.result?.username
                            tvEmail.text = result.data.data?.result?.email
                            tvPhone.text = result.data.data?.result?.noTelepon.toString()
                        }
                    }
                    is Result.Error -> {
                        showLoading(false)
                        Toast.makeText(
                            requireContext(),
                            result.error,
                            Toast.LENGTH_SHORT
                        ).show()                    }
                }
            }
        }
    }

    private fun setupButton() {
        binding.rowName.setOnClickListener(this)
        binding.rowEmail.setOnClickListener(this)
        binding.rowPhone.setOnClickListener(this)
        binding.rowHealth.setOnClickListener(this)
        binding.btnLogout.setOnClickListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
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

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.row_name -> {
                Toast.makeText(
                    requireContext(),
                    "Under Development",
                    Toast.LENGTH_SHORT
                ).show()
            }
            R.id.row_email -> {
                Toast.makeText(
                    requireContext(),
                    "Under Development",
                    Toast.LENGTH_SHORT
                ).show()
            }
            R.id.row_phone -> {
                Toast.makeText(
                    requireContext(),
                    "Under Development",
                    Toast.LENGTH_SHORT
                ).show()
            }
            R.id.row_health -> {
                Toast.makeText(
                    requireContext(),
                    "Under Development",
                    Toast.LENGTH_SHORT
                ).show()
            }
            R.id.btn_logout -> {
                userPreference.removeUserCookie()
                Toast.makeText(
                    requireContext(),
                    "Logout Success",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}