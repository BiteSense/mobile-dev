package com.c23ps323.bitesense.ui.auth.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.c23ps323.bitesense.MainActivity
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
        binding = FragmentLoginBinding.inflate(inflater,container,false)
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
                userPreference.saveUserCookie("id_user=132362637; token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyaWQiOjEzMjM2MjYzNywiaWF0IjoxNjg2MTA4NzA1LCJleHAiOjE2ODYxOTUxMDV9.JrRJprQWAH08UnKpBDHW0wmdYFYKfRW9HC3jUfWZ1Wo")
                Intent(requireContext(),PreferenceActivity::class.java).also {
                    startActivity(it)
                }
            }

        }
    }

}