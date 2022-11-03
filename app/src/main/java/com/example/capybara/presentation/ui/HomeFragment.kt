package com.example.capybara.presentation.ui.HomeFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.capybara.data.models.Category
import com.example.capybara.data.util.DataState
import com.example.capybara.databinding.FragmentHomeBinding
import com.example.capybara.presentation.adapter.CategoryAdapter
import com.example.capybara.presentation.viewmodels.HomeViewEvent
import com.example.capybara.presentation.viewmodels.HomeViewModel
import com.example.capybara.presentation.viewmodels.HomeViewState
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModels<HomeViewModel>()
    //private lateinit var categoryList: ArrayList<Category>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //viewModel.getCategoriesWithProducts()
        binding.MainScrollViewHomeFrag.isFillViewport=true
        lifecycleScope.launchWhenResumed {
            launch {
                viewModel.uiState.collect {
                    when (it) {
                        is HomeViewState.Success -> {
                            val categories = it.categories
                            if (categories != null) {
                                if(categories.size!=0)
                                    setCategoryRecycler(categories)
                            }
                            else{
                                //todo empty category page
                            }
                            binding.loadingPanel.visibility= GONE
                        }
                        is HomeViewState.Loading -> {
                            binding.loadingPanel.visibility=VISIBLE
                        }
                        is HomeViewState.Empty -> {

                        }
                    }
                }
            }

            launch {
                viewModel.uiEvent.collect {
                    when (it) {
                        is HomeViewEvent.ShowError -> {
                            Snackbar.make(
                                binding.root,
                                it.message.toString(),
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }

    private fun setCategoryRecycler(categories: ArrayList<Category>) {
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL, false)
        binding.categoryRecView.setLayoutManager(layoutManager)
        binding.categoryRecView.adapter = CategoryAdapter(requireContext(),categories)
    }
}