package com.c23ps323.bitesense.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.c23ps323.bitesense.R
import com.c23ps323.bitesense.data.Result
import com.c23ps323.bitesense.data.remote.response.HealthConditionResponse
import com.c23ps323.bitesense.databinding.FragmentProfileBinding
import com.c23ps323.bitesense.ui.auth.AuthActivity
import com.c23ps323.bitesense.ui.editProfile.EditProfileFragment
import com.c23ps323.bitesense.ui.preference.PreferenceActivity
import com.c23ps323.bitesense.utils.UserPreference
import com.c23ps323.bitesense.utils.ViewModelFactory
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val profileViewModel: ProfileViewModel by viewModels {
        ViewModelFactory(requireContext())
    }
    private lateinit var userPreference: UserPreference
    private val userHealthConditions = mutableListOf<String>()

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

        binding.btnLogout.setOnClickListener {
            userPreference.removeUserCookie()
            profileViewModel.deleteAllData()
            Toast.makeText(
                requireContext(),
                "Logout Success",
                Toast.LENGTH_SHORT
            ).show()
            startActivity(Intent(requireContext(), AuthActivity::class.java))
            requireActivity().finish()
        }

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
                            tvPhone.text = getString(
                                R.string.phone_number,
                                result.data.data?.result?.noTelepon.toString()
                            )
                            rowName.setOnClickListener {
                                navigateToEdit(
                                    "Username",
                                    result.data.data?.result?.username.toString()
                                )
                            }
                            rowPhone.setOnClickListener {
                                navigateToEdit(
                                    "Phone Number",
                                    result.data.data?.result?.noTelepon.toString()
                                )
                            }
                            rowEmail.setOnClickListener {
                                navigateToEdit("Email", result.data.data?.result?.email.toString())
                            }
                        }
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

        profileViewModel.getUserHealthCondition.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> showLoading(true)
                    is Result.Success -> {
                        showLoading(false)
                        dataConditionProcess(result.data)
                        binding.tvHealth.text = userHealthConditions.joinToString {
                            it
                        }
                        binding.rowHealth.setOnClickListener {
                            startActivity(Intent(requireContext(), PreferenceActivity::class.java))
                        }
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

    private fun dataConditionProcess(result: HealthConditionResponse) {
        val dataPenyakit = result.data?.dataPenyakit
        val dataKondisi = result.data?.dataKondisi
        val dataFood = result.data?.dataFood
        lifecycleScope.launch {
            if (dataFood!!.isNotEmpty()) {
                for (i in 0 until result.data.dataFood.size) {
                    userHealthConditions.add(result.data.dataFood[i]?.nameFood!!)
                }
            }
            if (dataPenyakit!!.isNotEmpty()) {
                for (i in 0 until result.data.dataPenyakit.size) {
                    userHealthConditions.add(result.data.dataPenyakit[i]?.namaPenyakit!!)
                }
            }
            if (dataKondisi!!.isNotEmpty()) {
                for (i in 0 until result.data.dataKondisi.size) {
                    userHealthConditions.add(result.data.dataKondisi[i]?.nameCondition!!)
                }
            }
        }
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

    override fun onPause() {
        super.onPause()
        userHealthConditions.clear()
    }

    private fun navigateToEdit(title: String, value: String) {
        val editProfileFragment = EditProfileFragment()
        val bundle = Bundle()
        bundle.putString(EditProfileFragment.EXTRA_TITLE, title)
        bundle.putString(EditProfileFragment.EXTRA_VALUE, value)
        editProfileFragment.arguments = bundle
        parentFragmentManager.beginTransaction()
            .replace(
                R.id.frame_container,
                editProfileFragment,
                EditProfileFragment::class.java.simpleName
            )
            .addToBackStack(null)
            .commit()
    }
}