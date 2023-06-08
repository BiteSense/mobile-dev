package com.c23ps323.bitesense.ui.editProfile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.c23ps323.bitesense.R
import com.c23ps323.bitesense.databinding.FragmentEditProfileBinding
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton

class EditProfileFragment : Fragment() {
    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!
    private var title: String? = null
    private var profileValue: String? = null

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

    companion object {
        const val EXTRA_TITLE = "extra_title"
        const val EXTRA_VALUE = "extra_value"
    }
}