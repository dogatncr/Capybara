package com.example.capybara.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.capybara.R
import com.example.capybara.data.models.CartProduct
import com.example.capybara.data.models.Product
import com.example.capybara.databinding.FragmentCartBinding
import com.example.capybara.databinding.FragmentProductDetailBinding
import com.example.capybara.presentation.adapter.CartAdapter
import com.example.capybara.presentation.viewmodels.CartViewModel
import com.example.capybara.presentation.viewmodels.DetailViewState
import com.example.capybara.presentation.viewmodels.HomeViewState
import com.example.capybara.presentation.viewmodels.ProductDetailViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ProductDetailFragment : Fragment() {
    private lateinit var binding : FragmentProductDetailBinding
    private val viewModel by viewModels<ProductDetailViewModel>()

    private lateinit var productData: Product
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        productData = ProductDetailFragmentArgs.fromBundle(requireArguments()).product
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentProductDetailBinding.bind(view)
        viewModel.getCartItem(productData.id)
        lifecycleScope.launchWhenResumed {
            launch {
                viewModel.uiState.collect {
                    when (it) {
                        is DetailViewState.CartContains -> setUpContains(it.quantity)
                        DetailViewState.CartNotContains -> setUpNotContains()
                        DetailViewState.Error -> setUpNotContains()
                        DetailViewState.Loading -> setUpNotContains()
                    }
                }
            }
        }
        binding.productDetailBack.setOnClickListener {
            findNavController().navigateUp()
        }


    }
    private fun setUpContains(quantity:Int){
        setUpViews()
        binding.quantityLayout.visibility=VISIBLE
        binding.quantityTvCart.text=quantity.toString()
        setOnclickListeners(true,quantity)

    }
    private fun setUpNotContains(){
        setUpViews()
        binding.quantityLayout.visibility= GONE
        setOnclickListeners(false,0)
    }
    private fun setUpViews() {
        val price="$"+ "${productData.price}"
        binding.productPrice.text = price
        binding.productName.text = productData.title
        binding.productDescription.text = productData.description
        binding.productRating.rating = productData.rating.rate.toFloat()

        Glide.with(binding.productDetailImage)
            .load(productData.image)
            .into(binding.productDetailImage)

    }
    private fun setOnclickListeners(cartContain:Boolean,quantity: Int){
        if(cartContain){
            binding.minusLayout.setOnClickListener{
                val cartItem = CartProduct(productData.id,productData.image,productData.price.toString(),productData.title,quantity)
                viewModel.updateProductInCart(false,cartItem)
            }
            binding.plusLayout.setOnClickListener{
                val cartItem = CartProduct(productData.id,productData.image,productData.price.toString(),productData.title,quantity+1)
                viewModel.updateProductInCart(true,cartItem)
            }

            binding.addCartButton.setOnClickListener {
                val cartItem = CartProduct(productData.id,productData.image,productData.price.toString(),productData.title,quantity+1)
                viewModel.updateProductInCart(true,cartItem)
                Snackbar.make(binding.addCartButton,"Added to Cart Successfully", Snackbar.LENGTH_SHORT).show()
            }
        }
        else{
            binding.addCartButton.setOnClickListener {
                val cartItem = CartProduct(productData.id,productData.image,productData.price.toString(),productData.title,1)
                viewModel.saveToCart(cartItem)
                Snackbar.make(binding.addCartButton,"Added to Cart Successfully", Snackbar.LENGTH_SHORT).show()
            }
        }
        binding.productDetailToCart.setOnClickListener {
            findNavController().navigate(R.id.action_productDetailFragment_to_cartFragment)
        }
    }

}