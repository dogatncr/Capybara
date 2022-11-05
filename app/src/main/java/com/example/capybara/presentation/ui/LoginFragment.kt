package com.example.capybara.presentation.ui.LoginFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.capybara.R
import com.example.capybara.databinding.FragmentLoginBinding
import com.example.capybara.presentation.viewmodels.HomeViewEvent
import com.example.capybara.presentation.viewmodels.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class LoginFragment : Fragment(){
    private lateinit var binding: FragmentLoginBinding
    private lateinit var navController: NavController
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()


        lifecycleScope.launchWhenResumed {
            launch {
                viewModel.uiEvent.collect {
                    when (it) {
                        is LoginViewModel.LoginViewEvent.NavigateToMain -> {
                            val uname=binding.etEmail.text.trim().toString().subSequence(0, binding.etEmail.text.trim().toString().indexOf('@'))
                            viewModel.setUsername(uname as String)
                            navController.navigate(
                                R.id.action_loginFragment_to_homeFragment,
                                null,
                                androidx.navigation.NavOptions.Builder().setPopUpTo(0, true).build()
                            )
                            Toast.makeText(requireContext(), "Login Success", Toast.LENGTH_SHORT)
                                .show()
                        }
                        is LoginViewModel.LoginViewEvent.ShowError -> {
                            Toast.makeText(requireContext(), "Login failed", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
            launch {
                viewModel.uiState.collect {
                    when (it) {
                        LoginViewModel.LoginUiState.Loading -> {
                            binding.loadingPanel.visibility=VISIBLE
                        }
                        else -> {binding.loadingPanel.visibility= GONE}
                    }
                }
            }
        }

        binding.btnLogin.setOnClickListener {
            viewModel.login(
                binding.etEmail.text.trim().toString(),
                binding.etPassword.text.trim().toString()
            )
        }

        binding.btnSignUp.setOnClickListener {
            navController.navigate(R.id.action_loginFragment_to_signupFragment)
        }
    }
}