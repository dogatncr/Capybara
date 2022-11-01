package com.example.capybara.data.models

class Category(var title: String, var itemList: ArrayList<Product>) {

    var categoryTitle: String = title
    var categoryItemList: ArrayList<Product> = itemList

    fun getProductList(): ArrayList<Product> {
        return categoryItemList
    }
}