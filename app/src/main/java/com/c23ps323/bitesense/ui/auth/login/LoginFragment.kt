package com.c23ps323.bitesense.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.c23ps323.bitesense.R
import com.c23ps323.bitesense.databinding.FragmentLoginBinding
import com.c23ps323.bitesense.ui.preference.PreferenceActivity
import com.c23ps323.bitesense.utils.UserPreference


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setActions()
    }

    private fun setActions() {
        binding.apply {
            btnRegister.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_loginFragment_to_registerFragment)
            )

            btnLogin.setOnClickListener {
                val userPreference = UserPreference(requireContext())
                userPreference.saveUserCookie("id_user=847096943; token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyaWQiOjg0NzA5Njk0MywiaWF0IjoxNjg2NTQ4ODI3LCJleHAiOjE2ODY2MzUyMjd9.Ytwab3AePXKZ-nrwy3-2vIOQ2SzQqGp9XRNk7wDrYYU")
                Intent(requireContext(), PreferenceActivity::class.java).also {
                    startActivity(it)
                }
            }

        }
    }

}