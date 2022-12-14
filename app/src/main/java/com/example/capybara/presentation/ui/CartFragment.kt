package com.example.capybara.presentation.ui

import android.R
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieDrawable.INFINITE
import com.example.capybara.data.models.CartProduct
import com.example.capybara.databinding.FragmentCartBinding
import com.example.capybara.presentation.adapter.CartAdapter
import com.example.capybara.presentation.viewmodels.CartViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
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
        binding.cartRecView.adapter = cartAdapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCartItems().observe(viewLifecycleOwner){
            if(it.size==0){
                setCartRecycler(it)
                binding.animationViewCartPage.playAnimation()
                binding.animationViewCartPage.repeatCount=INFINITE
                binding.emptyBagMsgLayout.visibility = View.VISIBLE
                binding.bottomCartLayout.visibility = View.GONE
                binding.cartClearAll.visibility = View.INVISIBLE
            }
            else{
                setCartRecycler(it)
                binding.bottomCartLayout.visibility = View.VISIBLE
                binding.cartRecView.visibility = View.VISIBLE
                binding.cartClearAll.visibility = View.VISIBLE
            }

        }

        viewModel.totalItems.observe(viewLifecycleOwner){
            binding.totalProduct.text = "Total $it Items"
        }

        viewModel.totalItemsPrice.observe(viewLifecycleOwner){
            val total='$'+it.toString()
            binding.totalPrice.text = total
        }


        setOnClickListeners()
    }

    private fun setCartRecycler(it: List<CartProduct>?) {
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context,
            LinearLayoutManager.VERTICAL, false)
        binding.cartRecView.setLayoutManager(layoutManager)
        cartAdapter.differ.submitList(it)
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
        binding.checkoutButton.setOnClickListener {
            val alert: AlertDialog.Builder = AlertDialog.Builder(context)
            alert.setMessage("Proceed to check out?")
                .setPositiveButton("Check out", { dialog, which ->
                    viewModel.clearCart()
                    Snackbar.make(requireView(), "Checked out succesfully", Snackbar.LENGTH_SHORT)
                        .show()
                }).setNegativeButton("Cancel", null)

            val alert1: AlertDialog = alert.create()
            alert1.show()
        }
    }
}