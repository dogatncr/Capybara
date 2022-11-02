package com.example.capybara.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.navigateUp
import com.airbnb.lottie.LottieDrawable.INFINITE
import com.example.capybara.databinding.FragmentCartBinding
import com.example.capybara.databinding.FragmentHomeBinding
import com.example.capybara.presentation.adapter.CartAdapter
import com.example.capybara.presentation.adapter.CategoryAdapter
import com.example.capybara.presentation.viewmodels.CartViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CartFragment : Fragment(){
    private lateinit var binding: FragmentCartBinding
    private val viewModel by viewModels<CartViewModel>()
    @Inject
    lateinit var cartAdapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCartItems().observe(viewLifecycleOwner){
            if(it.size==0){
                binding.animationViewCartPage.playAnimation()
                binding.animationViewCartPage.repeatCount=INFINITE
                binding.emptyBagMsgLayout.visibility = View.VISIBLE
                binding.bottomCartLayout.visibility = View.GONE
                binding.cartClearAll.visibility = View.GONE
            }
            cartAdapter.differ.submitList(it)
        }

        viewModel.totalItems.observe(viewLifecycleOwner){
            binding.totalProduct.text = "Total $it Items"
        }

        viewModel.totalItemsPrice.observe(viewLifecycleOwner){
            val total='$'+it.toString()
            binding.totalPrice.text = total
        }

        binding.cartRecView.adapter = cartAdapter
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        cartAdapter.setOnRemoveClickListener {
            viewModel.deleteCartItem(it)
        }

        cartAdapter.incrementClickListener {
            viewModel.incrementItem(it)
        }

        cartAdapter.decrementClickListener {
            viewModel.decrementItem(it)
        }
        binding.cartBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.cartClearAll.setOnClickListener {
            viewModel.clearCart()
        }
        binding.startShoppingButton.setOnClickListener{
            findNavController().navigateUp()
        }
    }
}