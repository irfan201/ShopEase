package com.example.shopease.presentation.checkout

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shopease.R
import com.example.shopease.data.model.OrderRequest
import com.example.shopease.databinding.ActivityCheckOutBinding
import com.example.shopease.databinding.LoadingDialogBinding
import com.example.shopease.domain.model.OrderState
import com.example.shopease.domain.model.Product
import com.example.shopease.presentation.SuccessActivity
import com.example.shopease.presentation.adapter.CheckOutAdapter
import com.example.shopease.presentation.payOrder.PayActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.log

@AndroidEntryPoint
class CheckOutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCheckOutBinding
    private val viewModel: CheckOutViewModel by viewModels()
    private val loadingDialog by lazy {
        val binding = LoadingDialogBinding.inflate(layoutInflater)
        AlertDialog.Builder(this)
            .setView(binding.root)
            .setCancelable(false)
            .create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckOutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val product =
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra(EXTRA_PRODUCT, Product::class.java)
            } else {
                intent.getParcelableExtra(EXTRA_PRODUCT)
            }
        val listProduct =
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableArrayListExtra(EXTRA_PRODUCT, Product::class.java)
            } else {
                intent.getParcelableArrayListExtra(EXTRA_PRODUCT)
            }
        if (product != null) {
            Log.d("product", product.toString())
            showProduct(listOf(product))
            val formattedPrice = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
            formattedPrice.maximumFractionDigits = 0
            val formatRupiah = formattedPrice.format(product.totalPrice)
            binding.tvPrice.text = formatRupiah
        } else if (listProduct != null) {
            showProduct(listProduct)
            val formattedPrice = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
            formattedPrice.maximumFractionDigits = 0
            val totalPrice = listProduct.sumOf { (it?.price ?: 1) * (it.quantity ?: 1) }
            val formatRupiah = formattedPrice.format(totalPrice)
            binding.tvPrice.text = formatRupiah
        }


        binding.apply {
            ivBack.setOnClickListener {
                finish()
            }
            btnPay.setOnClickListener {
                if (product != null) {
                    viewModel.orderProduct(
                        OrderRequest(
                            amount = product.totalPrice ?: 0,
                            email = "test@gmail.com",
                            items = listOf(
                                OrderRequest.Item(
                                    id = product.id,
                                    name = product.title,
                                    price = product.price,
                                    quantity = product.quantity ?: 0
                                )
                            )
                        )
                    )
                } else if (listProduct != null) {
                    val products = listProduct.map {
                        OrderRequest.Item(
                            id = it?.id ?: 0,
                            name = it?.title ?: "",
                            price = it?.price ?: 0,
                            quantity = it?.quantity ?: 0
                        )
                    }
                    Log.d("products", products.toString())
                    Log.d("listProduct", listProduct.sumOf { (it?.price ?: 1) * (it.quantity ?: 1) }.toString())
                    viewModel.orderProduct(OrderRequest(
                        amount = listProduct.sumOf { (it?.price ?: 1) * (it.quantity ?: 1) },
                        email = "test@gmail.com",
                        items = products
                    ))
                }
                orderProduct()
            }


        }

    }

    private fun orderProduct() {
        lifecycleScope.launch {
            viewModel.orderState.collect { value ->
                when (value) {
                    is OrderState.Error -> {
                        loadingDialog.dismiss()
                        Toast.makeText(this@CheckOutActivity, value.message, Toast.LENGTH_SHORT)
                            .show()
                    }

                    OrderState.Loading -> {
                        loadingDialog.show()
                    }

                    is OrderState.Success -> {
                        loadingDialog.dismiss()
                        val intent = Intent(this@CheckOutActivity, PayActivity::class.java)
                        intent.putExtra(PayActivity.EXTRA_ORDER, value.order.orUrl)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
    }


    private fun showProduct(listProduct: List<Product?>) {
        val adapter = CheckOutAdapter(listProduct)
        binding.rvProduct.layoutManager = LinearLayoutManager(this)
        binding.rvProduct.adapter = adapter

    }

    companion object {
        const val EXTRA_PRODUCT = "extra_product"

    }
}