package com.c23ps323.bitesense.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope

import com.c23ps323.bitesense.MainActivity
import com.c23ps323.bitesense.MainActivity.Companion.EXTRA_TOKEN

import androidx.fragment.app.Fragment
import androidx.navigation.Navigation

import com.c23ps323.bitesense.R
import com.c23ps323.bitesense.data.remote.response.LoginResponse
import com.c23ps323.bitesense.databinding.FragmentLoginBinding
import com.c23ps323.bitesense.ui.home.HomeViewModel
import com.c23ps323.bitesense.ui.preference.PreferenceActivity

import com.c23ps323.bitesense.utils.ViewModelFactory
import com.c23ps323.bitesense.utils.animateVisibility
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

import com.c23ps323.bitesense.utils.UserPreference



class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private var loginJob: Job = Job()
    private val loginViewModel: LoginViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }
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
            etPassword.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if (!p0.isNullOrEmpty() && p0.length < 8){
                        etPassword.error = getString(R.string.et_password_error_message)
                        inputPassword.isPasswordVisibilityToggleEnabled = false
                        btnLogin.isEnabled = false
                    }else if (p0!!.length >= 8){
                        btnLogin.isEnabled = true
                        inputPassword.isPasswordVisibilityToggleEnabled = true
                    }

                }

                override fun afterTextChanged(p0: Editable?) {

                }

            })
            etEmail.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if (!p0.isNullOrEmpty() && !Patterns.EMAIL_ADDRESS.matcher(p0).matches()){
                        etEmail.error = getString(R.string.et_email_error_message)
                        btnLogin.isEnabled = false
                    }else if (p0!!.length >= 8){
                        btnLogin.isEnabled = true
                    }

                }

                override fun afterTextChanged(p0: Editable?) {

                }

            })
            btnRegister.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_loginFragment_to_registerFragment)
            )

            btnLogin.setOnClickListener {
                    handleLogin()
            }

        }
    }

    private fun handleLogin() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString()
        setLoadingState(true)

        lifecycleScope.launchWhenResumed {

            if (loginJob.isActive) loginJob.cancel()

            loginJob = launch {
                loginViewModel.userLogin(email, password).collect { result ->

                    result.onSuccess { cookie ->
                        cookie.headers().get("set-cookie").let {
                            val id = it?.split(" ")

                            cookie.body()?.data?.token.let {credential ->
                                val token = "token=$credential"

                                val cookies = "${id?.get(0)} $token"
                                Log.d("cookies", cookies)
                                val userPreference = UserPreference(requireContext())
                                userPreference.saveUserCookie(cookies)
                                if (credential != null) {
                                    loginViewModel.saveAuthToken(credential)
                                }
                                Intent(requireContext(),PreferenceActivity::class.java).also {
                                    startActivity(it)
                                }
                            }

                        }





//                        credentials.data?.token?.let { token ->
//                            loginViewModel.saveAuthToken("")
//                            Intent(
//                                requireContext(),
//                                PreferenceActivity::class.java
//                            ).also { intent ->
//                                intent.putExtra(EXTRA_TOKEN, token)
//                                startActivity(intent)
//                                requireActivity().finish()
//                            }
//                        }
//
//                        Toast.makeText(
//                            requireContext(),
//                            getString(R.string.login_success_message),
//                            Toast.LENGTH_SHORT
//                        ).show()
                    }

                    result.onFailure {
                        Snackbar.make(
                            binding.root,
                            getString(R.string.login_error_message),
                            Snackbar.LENGTH_SHORT
                        ).show()

                        setLoadingState(false)
                    }

//                 val userPreference = UserPreference(requireContext())
//                 userPreference.saveUserCookie("id_user=847096943; token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyaWQiOjg0NzA5Njk0MywiaWF0IjoxNjg2NTQ4ODI3LCJleHAiOjE2ODY2MzUyMjd9.Ytwab3AePXKZ-nrwy3-2vIOQ2SzQqGp9XRNk7wDrYYU")
//                 Intent(requireContext(), PreferenceActivity::class.java).also {
//                     startActivity(it)
// >>>>>>> development
//                 }
                }
            }


        }
    }
    private fun setLoadingState(isLoading: Boolean) {
        binding.apply {
            etPassword.isEnabled = !isLoading
            etPassword.isEnabled = !isLoading
            btnLogin.isEnabled = !isLoading

            if (isLoading) {
                viewLoading.animateVisibility(true)
            } else {
                viewLoading.animateVisibility(false)
            }
        }
    }

}



