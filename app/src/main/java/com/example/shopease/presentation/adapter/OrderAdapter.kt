package com.example.shopease.presentation.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shopease.databinding.ItemOrderBinding
import com.example.shopease.domain.model.OrderHistory
import java.text.NumberFormat
import java.time.format.DateTimeFormatter
import java.util.Locale

class OrderAdapter(private val listOrder: List<OrderHistory>, private val listener: OrderListener) :
    RecyclerView.Adapter<OrderAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: ItemOrderBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount() = listOrder.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val order = listOrder[position]
        holder.binding.apply {
            tvNameOrder.text = order.orId
            tvStatusOrder.text = order.statusPayment
            val formatedPrice = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
            formatedPrice.maximumFractionDigits = 0
            val formatRupiah = formatedPrice.format(order.totalPrice)
            tvPriceOrder.text = formatRupiah
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val parsedDate = java.time.ZonedDateTime.parse(order.dateOrder)
                val dateFormat =
                    DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.getDefault())
                        .format(parsedDate)
                tvDateShop.text = dateFormat
            }
            root.setOnClickListener {
                listener.onClick(order.orId)
            }
        }
    }
}