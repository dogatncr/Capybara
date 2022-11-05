package com.example.capybara.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.capybara.data.models.Category
import com.example.capybara.data.models.CategoryChip
import com.example.capybara.data.models.Product
import com.example.capybara.data.util.DataState
import com.example.capybara.databinding.FragmentHomeBinding
import com.example.capybara.databinding.FragmentSearchBinding
import com.example.capybara.presentation.adapter.CategoryAdapter
import com.example.capybara.presentation.adapter.ProductsAdapter
import com.example.capybara.presentation.adapter.SearchAdapter
import com.example.capybara.presentation.viewmodels.HomeViewEvent
import com.example.capybara.presentation.viewmodels.HomeViewModel
import com.example.capybara.presentation.viewmodels.HomeViewState
import com.example.capybara.presentation.viewmodels.SearchViewState
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
@AndroidEntryPoint
class SearchFragment: Fragment()  {
    private lateinit var binding: FragmentSearchBinding
    private val viewModel by viewModels<HomeViewModel>()
    @Inject
    lateinit var adapter : SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }
    private var categoryChip = mutableListOf<CategoryChip>()
    private var productList =  arrayListOf<Product>()
    private var searchresult =  arrayListOf<Product>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.searchAllCategories()
        viewModel.searchAllProducts()
        lifecycleScope.launchWhenStarted {
            launch {
                viewModel.searchState.collect {
                    when (it) {
                        SearchViewState.Empty ->  Log.i("Search","Loading")
                        is SearchViewState.Error ->   Log.i("Search","${it.message?.status_message}")
                        SearchViewState.Loading ->  Log.i("Search","Loading")
                        is SearchViewState.Success -> {
                            if (it.products != null) {
                                productList=it.products
                                if(productList.size!=0)
                                    setRecycler()
                            }
                        }

                    }
                }
            }
        }

        viewModel.searchCategories.observe(viewLifecycleOwner){result ->
            when(result){
                is DataState.Success -> {
                    binding.chipGroup.isClickable=true
                    binding.chipGroup.removeAllViews()
                    val chip = Chip(requireContext())
                    chip.text = "All"
                    chip.id = 0
                    chip.isEnabled=true
                    chip.isCheckable=true
                    chip.isChecked = true
                    categoryChip.clear()
                    binding.chipGroup.addView(chip)
                    categoryChip.add(CategoryChip(0,"All"))
                    result.data.forEachIndexed { index, category ->
                        val chip = Chip(requireContext())
                        chip.text = category
                        chip.id = index+1
                        chip.isEnabled=true
                        chip.isCheckable=true
                        categoryChip.add(CategoryChip(index,category))
                        binding.chipGroup.addView(chip)
                    }
                    binding.chipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
                        group.isSingleSelection = true
                        val chipid = group.checkedChipId
                        val category = categoryChip[chipid].category

                        if(category=="All"){
                            adapter.differ.submitList(productList)
                        }
                        else{
                            val productList = productList.filter { it.category.equals(category,ignoreCase = true)} as ArrayList<Product>
                            adapter.differ.submitList(productList)
                        }
                    }
                }
                is DataState.Loading -> {
                    Log.i("Search","Loading")
                }
                is DataState.Error -> {
                    Log.i("Search","${result.error?.status_message}")
                }
            }
        }

        setListeners()
    }

    private fun setListeners() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchresult = productList.filter { it.title.contains("$query",ignoreCase = true)} as ArrayList<Product>
                adapter.differ.submitList(searchresult)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchresult = productList.filter { it.title.contains("$newText",ignoreCase = true)} as ArrayList<Product>
                adapter.differ.submitList(searchresult)
                return true
            }

        })
        binding.searchView.setOnCloseListener {
            binding.chipGroup.check(0)
            adapter.differ.submitList(productList)
            true
        }

    }

    private fun setRecycler() {
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context,
            LinearLayoutManager.VERTICAL, false)
        binding.searchRecyclerView.setLayoutManager(layoutManager)
        adapter.differ.submitList(productList)
        binding.searchRecyclerView.adapter = adapter
    }
}