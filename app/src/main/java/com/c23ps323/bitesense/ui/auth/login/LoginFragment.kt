package com.c23ps323.bitesense.ui.auth.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.c23ps323.bitesense.MainActivity
import com.c23ps323.bitesense.MainActivity.Companion.EXTRA_TOKEN
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
                    result.onSuccess { credentials  ->

                        credentials.data?.token?.let { token ->
                            loginViewModel.saveAuthToken(token)
                            Intent(requireContext(), PreferenceActivity::class.java).also { intent ->
                                intent.putExtra(EXTRA_TOKEN, token)
                                startActivity(intent)
                                requireActivity().finish()
                            }
                        }

                        Toast.makeText(
                            requireContext(),
                            getString(R.string.login_success_message),
                            Toast.LENGTH_SHORT
                        ).show()
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



