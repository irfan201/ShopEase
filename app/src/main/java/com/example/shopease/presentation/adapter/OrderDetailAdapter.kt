package com.example.shopease.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shopease.databinding.ItemOrderDetailBinding
import com.example.shopease.domain.model.OrderHistory
import java.text.NumberFormat
import java.util.Locale

class OrderDetailAdapter(private val listOrder: List<OrderHistory.OrderDetail>) :
    RecyclerView.Adapter<OrderDetailAdapter.MyViewHolder>() {
    class MyViewHolder( val binding: ItemOrderDetailBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemOrderDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount() = listOrder.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val order = listOrder[position]
        holder.binding.apply {
            tvNameOrderDetail.text = order.name
            val formatedPrice = NumberFormat.getCurrencyInstance(Locale("id","ID"))
            formatedPrice.maximumFractionDigits = 0
            val formatRupiah = formatedPrice.format(order.price)
            tvPriceOrderDetail.text = formatRupiah
            tvTotalOrder.text = order.quantity.toString()
            Glide.with(holder.itemView.context)
                .load(order.imageUrl)
                .into(ivOrderDetail)
        }
    }
}