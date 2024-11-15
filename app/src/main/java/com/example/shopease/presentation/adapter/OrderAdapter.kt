package com.example.shopease.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shopease.databinding.ItemOrderBinding
import com.example.shopease.domain.model.OrderHistory
import java.text.NumberFormat
import java.util.Locale

class OrderAdapter(private val listOrder:List<OrderHistory>):RecyclerView.Adapter<OrderAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: ItemOrderBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemOrderBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount() = listOrder.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val order = listOrder[position]
        holder.binding.apply {
            tvNameOrder.text = order.nameOrder
            tvPaymentOrder.text = order.quantity.toString()
            tvStatusOrder.text = order.statusPayment
            val formatedPrice = NumberFormat.getCurrencyInstance(Locale("id","ID"))
            formatedPrice.maximumFractionDigits = 0
            val formatRupiah = formatedPrice.format(order.totalPrice)
            tvPriceOrder.text = formatRupiah
        }
    }
}