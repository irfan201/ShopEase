package com.example.shopease.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shopease.databinding.ItemProductBinding
import com.example.shopease.domain.model.Product
import java.text.NumberFormat
import java.util.Locale

class ProductAdapter(private val listProduct: List<Product>, private val listener: ItemListener) :
    RecyclerView.Adapter<ProductAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = listProduct[position]
        holder.binding.apply {
            Glide.with(holder.itemView.context)
                    .load(data.image[0])
                .into(ivProduct)
            tvNameProduct.text = data.title
            val formattedPrice =
                NumberFormat.getCurrencyInstance(Locale("in", "ID"))
            formattedPrice.maximumFractionDigits = 0
            val formatRupiah = formattedPrice.format(data.price)
            tvPrice.text = formatRupiah
            rateProduct.rating = data.rating
            tvRate.text = data.rating.toString()
            root.setOnClickListener {
                listener.onItemClick(data.id)
            }
        }

    }

    override fun getItemCount() = listProduct.size
}