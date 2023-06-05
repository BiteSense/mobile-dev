package com.c23ps323.bitesense.ui.profile

import android.graphics.PorterDuff
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.c23ps323.bitesense.R
import com.c23ps323.bitesense.databinding.FragmentProfileBinding

class ProfileFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupButton()
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
                Toast.makeText(
                    requireContext(),
                    "Under Development",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}