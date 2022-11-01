package com.example.capybara.presentation.ui.HomeFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.capybara.data.models.Category
import com.example.capybara.data.util.DataState
import com.example.capybara.databinding.FragmentHomeBinding
import com.example.capybara.presentation.adapter.CategoryAdapter
import com.example.capybara.presentation.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModels<HomeViewModel>()
    private lateinit var categoryList: ArrayList<Category>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getCategoriesWithProducts()

        viewModel.categoryList.observe(viewLifecycleOwner){response ->
            when(response){
                is DataState.Success -> {
                    val categories = response.data
                    setCategoryRecycler(categories)
                }
                is DataState.Loading -> {
                    Log.i("HomeFragment","Loading...")
                }
                is DataState.Error -> {
                    Log.i("HomeFragment","error")
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