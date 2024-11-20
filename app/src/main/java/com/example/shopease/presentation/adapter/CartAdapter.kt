package com.example.shopease.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shopease.data.model.ProductCartEntity
import com.example.shopease.databinding.ItemCartBinding
import com.example.shopease.domain.model.Product
import com.example.shopease.utils.ShopHelper.formatPrice

class CartAdapter(private val listProduct: List<Product>,private val listener: CartListener):RecyclerView.Adapter<CartAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount() = listProduct.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val product = listProduct[position]
        var quantity = product.quantity
        holder.binding.apply {
            tvNameCart.text = product.title
            tvPriceCart.text = formatPrice(product.price)
            Glide.with(holder.itemView.context)
                .load(product.image[0])
                .into(ivCart)
            cvDelete.setOnClickListener {
                listener.onDelete(ProductCartEntity(
                    productId = product.id,
                    productName = product.title,
                    productPrice = product.price,
                    productDescription = product.description,
                    productCategory = product.category,
                    productImage = product.image[0],
                    productRating = product.rating,
                    userId = product.userId ?: ""
                ))
            }
            ivAdd.setOnClickListener {
                quantity++
                tvQuantity.text = quantity.toString()
                val totalPrice = product.price * quantity
                val formatPlus = formatPrice(totalPrice)
                tvPriceCart.text = formatPlus
                product.quantity = quantity
                listener.onPlus(listProduct)
            }

            ivMin.setOnClickListener {
                if (quantity > 1) {
                    quantity--
                    tvQuantity.text = quantity.toString()
                    val totalPrice = product.price * quantity
                    val formatMin = formatPrice(totalPrice)
                    tvPriceCart.text = formatMin
                    product.quantity = quantity
                }
                listener.onMinus(listProduct)
            }
        }
    }



}