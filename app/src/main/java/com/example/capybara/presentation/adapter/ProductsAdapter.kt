package com.example.capybara.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.capybara.R
import com.example.capybara.data.models.Product
import com.example.capybara.databinding.ProductElementBinding
import com.example.capybara.presentation.ui.HomeFragment.HomeFragmentDirections

class ProductsAdapter(private val productList: ArrayList<Product>, context: Context): RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {
    private lateinit var binding: ProductElementBinding
    val ctx: Context = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ProductElementBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product: Product = productList[position]
        holder.productName.text = product.title
        holder.productPrice.text = "$"+product.price
        holder.productRating.rating = product.rating.rate.toFloat()

        Glide.with(ctx)
            .load(product.image)
            .into(holder.productImage)

        holder.productCard.setOnClickListener {
            //Navigation.findNavController( holder.productCard).navigate(HomeFragmentDirections.actionHomeFragmentToProductDetailFragment(product));
            Navigation.createNavigateOnClickListener(HomeFragmentDirections.actionHomeFragmentToProductDetailFragment(product)).onClick(holder.productCard)
            //onItemClickListener(product)
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }


    class ViewHolder(private val binding: ProductElementBinding):RecyclerView.ViewHolder(binding.root) {
        val productImage: ImageView = binding.productImage
        val productCard: CardView =binding.cardView
        val productRating: RatingBar =binding.productRating
        val productName: TextView = binding.productName
        val productPrice: TextView =binding.productPrice
    }
}