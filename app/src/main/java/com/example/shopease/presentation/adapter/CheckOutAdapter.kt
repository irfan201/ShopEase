package com.example.shopease.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shopease.databinding.ItemCheckoutBinding
import com.example.shopease.domain.model.Product
import com.example.shopease.utils.ShopHelper.formatPrice

class CheckOutAdapter(val listProduct: List<Product?>) : RecyclerView.Adapter<CheckOutAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: ItemCheckoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemCheckoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount() = listProduct.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val product = listProduct[position]
        holder.binding.apply {
            Glide.with(holder.itemView.context)
                .load(product?.image?.get(0))
                .into(ivCheckout)
            tvNameCheckout.text = product?.title
            val formatRupiah = product?.price?.let { formatPrice(it) }
            tvPriceCheckout.text = formatRupiah
            tvCategoryCheckout.text = product?.category
            val productQuantity = product?.quantity ?: 1
            if (productQuantity > 1){
                tvQuantity.text = "X ${product?.quantity}"
            }
        }
    }
}