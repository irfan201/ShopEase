package com.example.shopease.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shopease.databinding.ItemFavoriteBinding
import com.example.shopease.domain.model.Product
import java.text.NumberFormat
import java.util.Locale

class FavoriteAdapter(private val listProduct: List<Product>,private val listener: ItemListener):RecyclerView.Adapter<FavoriteAdapter.MyViewHolder>() {
    class MyViewHolder(val binding : ItemFavoriteBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount() = listProduct.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val product = listProduct[position]

        holder.binding.apply {
            tvNameFavorite.text = product.title
            val formattedPrice =
                NumberFormat.getCurrencyInstance(Locale("in", "ID"))
            formattedPrice.maximumFractionDigits = 0
            val formatRupiah = formattedPrice.format(product.price)
            tvPriceFavorite.text = formatRupiah
            tvCategoryFavorite.text = product.category
            Glide.with(holder.itemView.context)
                .load(product.image[0])
                .into(ivFavorite)
        }
        holder.binding.root.setOnClickListener {
            listener.onItemClick(product.id)
        }

    }
}