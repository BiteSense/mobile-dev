package com.c23ps323.bitesense.ui.editProfile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.c23ps323.bitesense.R
import com.c23ps323.bitesense.data.Result
import com.c23ps323.bitesense.databinding.FragmentEditProfileBinding
import com.c23ps323.bitesense.ui.profile.ProfileFragment
import com.c23ps323.bitesense.utils.ViewModelFactory
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton

class EditProfileFragment : Fragment() {
    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!
    private var title: String? = null
    private var profileValue: String? = null

    private val editProfileViewModel: EditProfileViewModel by viewModels {
        ViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideBottomNav(true)
        title = arguments?.getString(EXTRA_TITLE)
        profileValue = arguments?.getString(EXTRA_VALUE)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onBackPressed()
        binding.etProfile.setText(profileValue)
        binding.tvTitle.text = getString(R.string.change_title, title)
        binding.btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
            hideBottomNav(false)
        }
        binding.btnSubmit.setOnClickListener {
            editProfileViewModel.editUsername(binding.etProfile.text.toString()).observe(viewLifecycleOwner) { result ->
                if (result != null) {
                    when(result) {
                        is Result.Loading -> showLoading(true)
                        is Result.Success -> {
                            showLoading(false)
                            parentFragmentManager.beginTransaction()
                                .replace(R.id.frame_container, ProfileFragment(), ProfileFragment::class.java.simpleName)
                                .commit()
                            val fragmentManager = requireActivity().supportFragmentManager
                            for (i in 0 until fragmentManager.backStackEntryCount) {
                                fragmentManager.popBackStack()
                            }
                            hideBottomNav(false)
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
    }

    private fun onBackPressed() {
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true)
            {
                override fun handleOnBackPressed() {
                    hideBottomNav(false)
                    parentFragmentManager.popBackStack()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            callback
        )
    }

    private fun hideBottomNav(isShow: Boolean) {
        val bottomAppBar = activity?.findViewById<BottomAppBar>(R.id.bottom_appbar)
        val fab = activity?.findViewById<FloatingActionButton>(R.id.fab)
        if (isShow) {
            bottomAppBar?.visibility = View.GONE
            fab?.visibility = View.GONE
        } else {
            bottomAppBar?.visibility = View.VISIBLE
            fab?.visibility = View.VISIBLE
        }
    }

    private fun showLoading(show: Boolean) {
        if (show) {
            binding.apply {
                progressBar.visibility = View.VISIBLE
                linearLayout.visibility = View.GONE
                btnSubmit.visibility = View.GONE
                etProfile.visibility = View.GONE
            }
        } else {
            binding.apply {
                progressBar.visibility = View.GONE
                linearLayout.visibility = View.VISIBLE
                btnSubmit.visibility = View.VISIBLE
                etProfile.visibility = View.VISIBLE
            }
        }
    }

    companion object {
        const val EXTRA_TITLE = "extra_title"
        const val EXTRA_VALUE = "extra_value"
    }
}