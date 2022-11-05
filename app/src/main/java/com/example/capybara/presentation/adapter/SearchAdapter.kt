package com.example.capybara.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.capybara.data.models.Product
import com.example.capybara.databinding.ProductElementBinding
import com.example.capybara.databinding.SearchProductBinding
import com.example.capybara.presentation.ui.HomeFragment.HomeFragmentDirections
import com.example.capybara.presentation.ui.SearchFragmentDirections

class SearchAdapter: RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {
    private lateinit var binding: SearchProductBinding

    private val callback = object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this,callback)
    inner class SearchViewHolder(private val binding : SearchProductBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(product: Product){
            binding.productName.text = product.title
            binding.productPrice.text = "$"+product.price
            binding.productRating.rating = product.rating.rate.toFloat()

            Glide.with(binding.productImage)
                .load(product.image)
                .into(binding.productImage)

            binding.cardView.setOnClickListener {
                //Navigation.findNavController( holder.productCard).navigate(HomeFragmentDirections.actionHomeFragmentToProductDetailFragment(product));
                Navigation.createNavigateOnClickListener(SearchFragmentDirections.actionSearchFragmentToProductDetailFragment(product)).onClick(binding.cardView)
                //onItemClickListener(product)
            }

        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = SearchProductBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SearchViewHolder(binding)
    }
    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val product = differ.currentList[position]
        holder.bindData(product)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

}