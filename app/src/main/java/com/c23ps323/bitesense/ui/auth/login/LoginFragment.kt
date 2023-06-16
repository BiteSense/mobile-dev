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
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.c23ps323.bitesense.R
import com.c23ps323.bitesense.databinding.FragmentLoginBinding
import com.c23ps323.bitesense.ui.preference.PreferenceActivity
import com.c23ps323.bitesense.utils.UserPreference
import com.c23ps323.bitesense.utils.ViewModelFactory
import com.c23ps323.bitesense.utils.animateVisibility
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


@Suppress("DEPRECATION")
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
            etPassword.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if (!p0.isNullOrEmpty() && p0.length < 8) {
                        etPassword.error = getString(R.string.et_password_error_message)
                        inputPassword.isPasswordVisibilityToggleEnabled = false
                        btnLogin.isEnabled = false
                    } else if (p0!!.length >= 8) {
                        btnLogin.isEnabled = true
                        inputPassword.isPasswordVisibilityToggleEnabled = true
                    }

                }

                override fun afterTextChanged(p0: Editable?) {}

            })
            etEmail.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if (!p0.isNullOrEmpty() && !Patterns.EMAIL_ADDRESS.matcher(p0).matches()) {
                        etEmail.error = getString(R.string.et_email_error_message)
                        btnLogin.isEnabled = false
                    } else if (p0!!.length >= 8) {
                        btnLogin.isEnabled = true
                    }
                }

                override fun afterTextChanged(p0: Editable?) {}

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
                        cookie.headers()["set-cookie"].let {
                            val id = it?.split(" ")

                            cookie.body()?.data?.token.let { credential ->
                                val token = "token=$credential"
                                val cookies = "${id?.get(0)} $token;"
                                val userPreference = UserPreference(requireContext())
                                userPreference.saveUserCookie(cookies)
                                Log.d("Cookie", userPreference.getUserCookie())
                                if (credential != null) {
                                    loginViewModel.saveAuthToken(credential)
                                }
                                Intent(
                                    requireContext(),
                                    PreferenceActivity::class.java
                                ).also { intent ->
                                    startActivity(intent)
                                }
                            }
                        }
                    }

                    result.onFailure {
                        Snackbar.make(
                            binding.root,
                            getString(R.string.login_error_message),
                            Snackbar.LENGTH_SHORT
                        ).show()

                        setLoadingState(false)
                    }
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