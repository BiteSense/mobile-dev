package com.c23ps323.bitesense.ui.auth.register

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.c23ps323.bitesense.R
import com.c23ps323.bitesense.databinding.FragmentRegisterBinding
import com.c23ps323.bitesense.ui.auth.login.LoginViewModel
import com.c23ps323.bitesense.utils.ViewModelFactory
import com.c23ps323.bitesense.utils.animateVisibility
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class RegisterFragment : Fragment() {

    private lateinit var binding : FragmentRegisterBinding
    private var registerJob: Job = Job()
    private val registerViewModel: RegisterViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setActions()
    }

    private fun setActions() {
        binding.apply {

            edtPassword.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if (!p0.isNullOrEmpty() && p0.length < 8){
                        edtPassword.error = getString(R.string.et_password_error_message)
                        inputPassword.isPasswordVisibilityToggleEnabled = false
                        btnRegister.isEnabled = false
                    }else if (p0!!.length >= 8){
                        btnRegister.isEnabled = true
                        inputPassword.isPasswordVisibilityToggleEnabled = true
                    }

                }

                override fun afterTextChanged(p0: Editable?) {

                }

            })
            edtRepassword.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if (!p0.isNullOrEmpty() && p0.length < 8){
                        edtRepassword.error = getString(R.string.et_password_error_message)
                        inputRepassword.isPasswordVisibilityToggleEnabled = false
                        btnRegister.isEnabled = false
                    }else if (p0!!.length >= 8){
                        btnRegister.isEnabled = true
                        inputRepassword.isPasswordVisibilityToggleEnabled = true
                    }

                }

                override fun afterTextChanged(p0: Editable?) {

                }

            })
            edtEmail.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if (!p0.isNullOrEmpty() && !Patterns.EMAIL_ADDRESS.matcher(p0).matches()){
                        edtEmail.error = getString(R.string.et_email_error_message)
                        btnRegister.isEnabled = false
                    }else if (p0!!.length >= 8){
                        btnRegister.isEnabled = true
                    }

                }

                override fun afterTextChanged(p0: Editable?) {

                }

            })
            edtUsername.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if (p0.isNullOrEmpty()){
                        edtUsername.error = getString(R.string.et_username_error_message)
                        btnRegister.isEnabled = false
                    }else if (p0!!.length >= 8){
                        btnRegister.isEnabled = true
                    }

                }

                override fun afterTextChanged(p0: Editable?) {

                }

            })
            btnLogin.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_registerFragment_to_loginFragment)
            )

            btnRegister.setOnClickListener {
                handleRegister()
            }

        }
    }

    private fun handleRegister() {
        val name = binding.edtUsername.text.toString().trim()
        val email = binding.edtEmail.text.toString().trim()
        val password = binding.edtPassword.text.toString().trim()
        val repassword = binding.edtRepassword.text.toString().trim()
        setLoadingState(true)

        lifecycleScope.launchWhenResumed {

            if (registerJob.isActive) registerJob.cancel()

            registerJob = launch {
                registerViewModel.userRegister(name, email, password,repassword).collect { result ->
                    result.onSuccess {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.registration_success),
                            Toast.LENGTH_SHORT
                        ).show()

                        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                    }

                    result.onFailure {
                        Snackbar.make(
                            binding.root,
                            getString(R.string.registration_error_message),
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
            edtEmail.isEnabled = !isLoading
            edtPassword.isEnabled = !isLoading
            edtRepassword.isEnabled = !isLoading
            edtUsername.isEnabled = !isLoading
            btnRegister.isEnabled = !isLoading

            if (isLoading) {
                viewLoading.animateVisibility(true)
            } else {
                viewLoading.animateVisibility(false)
            }
        }
    }

}