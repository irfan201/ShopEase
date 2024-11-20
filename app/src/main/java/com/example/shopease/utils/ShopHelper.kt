package com.example.shopease.utils

import java.text.NumberFormat
import java.util.Locale

object ShopHelper {

    fun formatPrice(price: Int): String {
        val formattedPrice =
            NumberFormat.getCurrencyInstance(Locale("in", "ID"))
        formattedPrice.maximumFractionDigits = 0
        val formatRupiah = formattedPrice.format(price)
        return formatRupiah
    }

}