package com.example.capybara.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.capybara.R

import com.example.capybara.databinding.FragmentSignupBinding
import com.example.capybara.presentation.viewmodels.SignUpViewModel
import com.example.capybara.presentation.viewmodels.SignupViewEvent
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignupBinding
    private val viewModel by viewModels<SignUpViewModel>()
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()

        lifecycleScope.launchWhenResumed {
            launch {
                viewModel.uiEvent.collect {
                    when (it) {
                        is SignupViewEvent.NavigateToMain -> {
                            navController.navigate(
                                resId = R.id.action_signupFragment_to_homeFragment,
                                null,
                                navOptions = NavOptions.Builder().setPopUpTo(0, true).build()
                            )
                            Snackbar.make(requireView(), "SignUp Success", Snackbar.LENGTH_SHORT)
                                .show()

                        }
                        is SignupViewEvent.ShowError -> {
                            Snackbar.make(requireView(), it.error, Snackbar.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
        }

        binding.btnSignUp.setOnClickListener {
            viewModel.signup(
                binding.etEmail.text.trim().toString(),
                binding.etPassword.text.trim().toString(),
            )
        }
    }


}