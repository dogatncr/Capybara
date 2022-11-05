package com.example.capybara.presentation.ui

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.capybara.R
import com.example.capybara.data.util.DataStoreManager
import com.example.capybara.databinding.FragmentProfileBinding
import com.example.capybara.presentation.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class ProfileFragment: Fragment() {
    private lateinit var binding : FragmentProfileBinding
    @Inject
    lateinit var firebaseAuth: FirebaseAuth
    @Inject
    lateinit var dataStoreManager: DataStoreManager

    private val viewModel by viewModels<MainViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        lifecycleScope.launchWhenStarted {
            launch {
                binding.profileName.text=dataStoreManager.getUserName.first()
        }
        firebaseAuth.currentUser?.let {
            binding.profileEmail.text=it.email
            }
        }
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.btnLogout.setOnClickListener{
            val alert: AlertDialog.Builder = AlertDialog.Builder(context)
            alert.setMessage("Do you want to Log out?")
                .setPositiveButton("Logout", DialogInterface.OnClickListener { dialog, which ->
                    viewModel.logout()
                    while (findNavController().currentBackStackEntry != null) {
                        findNavController().popBackStack()
                    }
                    /*navController.setGraph(R.id.login_graph)
                    navController.popBackStack()*/
                    findNavController().navigate(R.id.login_graph)
                    //findNavController().navigate(R.id.action_profileFragment_to_login_graph)
                    Snackbar.make(requireView(), "Log out succesfully", Snackbar.LENGTH_SHORT)
                        .show()
                }).setNegativeButton("Cancel", null)

            val alert1: AlertDialog = alert.create()
            alert1.show()
        }
    }
}