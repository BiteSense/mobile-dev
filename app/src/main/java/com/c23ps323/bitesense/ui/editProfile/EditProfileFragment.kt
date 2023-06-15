package com.c23ps323.bitesense.ui.editProfile

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
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
        setupUI()
    }

    private fun setupUI() {
        binding.etProfile.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s?.trim().isNullOrEmpty()) {
                    btnEnable(true)
                } else {
                    btnEnable(false)
                }
            }

            override fun afterTextChanged(s: Editable?) {}

        })

        binding.apply {
            etProfile.setText(profileValue)
            tvTitle.text = getString(R.string.change_title, title)
            btnBack.setOnClickListener {
                parentFragmentManager.popBackStack()
                hideBottomNav(false)
            }
            btnSubmit.setOnClickListener {
                val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(it.windowToken, 0)
                when (title) {
                    USERNAME -> usernameEdit()
                    EMAIL -> emailEdit()
                    else -> teleponEdit()
                }
            }
        }
    }

    private fun btnEnable(state: Boolean) {
        if (state) {
            binding.btnSubmit.apply {
                isEnabled = true
                alpha = 1f
                setBackgroundResource(R.drawable.rounded_button)
                text = getString(R.string.submit)
            }
        } else {
            binding.btnSubmit.apply {
                isEnabled = false
                alpha = 0.6f
                setBackgroundResource(R.drawable.rounded_button)
                text = getString(R.string.fill_it)
            }
        }
    }

    private fun successEdit() {
        showLoading(false)
        Toast.makeText(
            requireContext(),
            getString(R.string.success_edit),
            Toast.LENGTH_SHORT
        ).show()
        val fragmentManager = requireActivity().supportFragmentManager
        for (i in 0 until fragmentManager.backStackEntryCount) {
            fragmentManager.popBackStack()
        }
        parentFragmentManager.beginTransaction()
            .replace(
                R.id.frame_container,
                ProfileFragment(),
                ProfileFragment::class.java.simpleName
            )
            .commit()
        hideBottomNav(false)
    }

    private fun editFail(msg: String) {
        showLoading(false)
        Toast.makeText(
            requireContext(),
            msg,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun teleponEdit() {
        editProfileViewModel.editTelepon(binding.etProfile.text.trim().toString())
            .observe(viewLifecycleOwner) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> showLoading(true)
                        is Result.Success -> successEdit()
                        is Result.Error -> editFail(getString(R.string.general_error))
                    }
                }
            }
    }

    private fun emailEdit() {
        editProfileViewModel.editEmail(binding.etProfile.text.trim().toString())
            .observe(viewLifecycleOwner) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> showLoading(true)
                        is Result.Success -> successEdit()
                        is Result.Error -> editFail(getString(R.string.general_error))
                    }
                }
            }
    }

    private fun usernameEdit() {
        editProfileViewModel.editUsername(binding.etProfile.text.trim().toString())
            .observe(viewLifecycleOwner) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> showLoading(true)
                        is Result.Success -> successEdit()
                        is Result.Error -> editFail(getString(R.string.general_error))
                    }
                }
            }
    }

    private fun onBackPressed() {
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
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
        private const val USERNAME = "Username"
        private const val EMAIL = "email"
    }
}