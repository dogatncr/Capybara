package com.example.capybara.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.capybara.R
import com.example.capybara.data.models.Category
import com.example.capybara.data.models.Product
import com.example.capybara.databinding.CategoryElementBinding


class CategoryAdapter(var ctx: Context, private val categoryList: ArrayList<Category>) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return CategoryAdapter.ViewHolder(
            CategoryElementBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val categoryName: String = categoryList[position].categoryTitle
        holder.categoryName.text=categoryName
        setCategoryItem(holder.productsRV, categoryList.get(position).getProductList());
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    public class ViewHolder(private val binding:CategoryElementBinding ): RecyclerView.ViewHolder(binding.root){
        val categoryName: TextView = binding.categoryName
        val productsRV:RecyclerView =itemView.findViewById(R.id.productsRecView)
    }

    private fun setCategoryItem(
        recyclerView: RecyclerView,
        categoryItemList: ArrayList<Product>
    ) {
        val productRecyclerAdapter = ProductsAdapter(categoryItemList,ctx)
        recyclerView.layoutManager = LinearLayoutManager(ctx, RecyclerView.HORIZONTAL, false)
        recyclerView.adapter = productRecyclerAdapter
    }
}