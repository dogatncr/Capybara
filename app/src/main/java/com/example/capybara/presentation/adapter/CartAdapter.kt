package com.example.capybara.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.capybara.data.models.CartProduct
import com.example.capybara.databinding.CartItemBinding

class CartAdapter : RecyclerView.Adapter<CartAdapter.CartViewHolder>(){

    private val callback = object : DiffUtil.ItemCallback<CartProduct>() {
        override fun areItemsTheSame(oldItem: CartProduct, newItem: CartProduct): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CartProduct, newItem: CartProduct): Boolean {
            return oldItem == newItem
        }

    }
    //for helping computing the difference between two cart state on background thread.
    val differ = AsyncListDiffer(this, callback)
    private var incrementListener : ((CartProduct) -> Unit) = {}
    private var decrementListener : ((CartProduct) -> Unit) = {}
    private var removeListener : ((CartProduct) -> Unit) = {}

    fun setOnRemoveClickListener(listener : (CartProduct) -> Unit){
        removeListener = listener
    }

    fun incrementClickListener(listener : (CartProduct) -> Unit){
        incrementListener = listener
    }

    fun decrementClickListener(listener : (CartProduct) -> Unit){
        decrementListener = listener
    }

    inner class CartViewHolder(private val binding : CartItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindData(cartItem: CartProduct){

            val price="$"+ cartItem.price
            binding.cartName.text = cartItem.title
            binding.cartPrice.text = price
            binding.quantityTvCart.text = "${cartItem.quantity}"

            Glide.with(binding.cartImage)
                .load(cartItem.image)
                .into(binding.cartImage)

            binding.cartClear.setOnClickListener {
                removeListener(cartItem)
            }

            binding.plusLayout.setOnClickListener {
                incrementListener(cartItem)
            }

            binding.minusLayout.setOnClickListener {
                decrementListener(cartItem)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartItem = differ.currentList[position]
        holder.bindData(cartItem)

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}