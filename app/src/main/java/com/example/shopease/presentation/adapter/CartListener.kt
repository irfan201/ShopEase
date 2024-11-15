package com.example.shopease.presentation.adapter

import com.example.shopease.data.model.ProductCartEntity
import com.example.shopease.domain.model.Product

interface CartListener {
    fun onDelete(product: ProductCartEntity)
    fun onPlus(product: List<Product>)
    fun onMinus(product: List<Product>)
}