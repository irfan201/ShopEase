package com.example.shopease.presentation.cart

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shopease.data.model.ProductCartEntity
import com.example.shopease.databinding.ActivityCartBinding
import com.example.shopease.domain.model.Product
import com.example.shopease.domain.model.ProductState
import com.example.shopease.presentation.checkout.CheckOutActivity
import com.example.shopease.presentation.adapter.CartAdapter
import com.example.shopease.presentation.adapter.CartListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

@AndroidEntryPoint
class CartActivity : AppCompatActivity(),CartListener {
    private lateinit var binding: ActivityCartBinding
    private val viewModel: CartViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.ivBack.setOnClickListener {
            finish()
        }
        lifecycleScope.launch {
            viewModel.getProductCarts(viewModel.getCurrentUser()?.uid ?: "")
            viewModel.productState.collect { value ->
                when (value) {
                    is ProductState.Error -> {
                        Toast.makeText(this@CartActivity, value.message, Toast.LENGTH_SHORT).show()
                    }

                    ProductState.Loading -> {
                    }
                    is ProductState.Success -> {
                        if (value.data.isEmpty()){
                            binding.cvButton.isVisible = false
                            binding.ivCartEmpty.isVisible = true
                            binding.tvEmpty.isVisible = true
                            binding.rvCart.isVisible = false
                        } else{
                            binding.cvButton.isVisible = true
                            binding.ivCartEmpty.isVisible = false
                            binding.tvEmpty.isVisible = false
                            binding.rvCart.isVisible = true
                            showData(value.data)
                            val totalPrice = value.data.sumOf { it.price * (it.quantity) }
                            val formattedPrice = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
                            formattedPrice.maximumFractionDigits = 0
                            val formatRupiah = formattedPrice.format(totalPrice)
                            binding.tvPrice.text = formatRupiah
                            binding.btnCheckout.setOnClickListener {
                                val intent = Intent(this@CartActivity, CheckOutActivity::class.java)
                                intent.putParcelableArrayListExtra(CheckOutActivity.EXTRA_PRODUCT, ArrayList(value.data))
                                startActivity(intent)
                            }
                        }

                    }
                }
            }
        }

    }

    private fun showData(listProduct: List<Product>) {
        val adapter = CartAdapter(listProduct,this)
        binding.rvCart.layoutManager = LinearLayoutManager(this)
        binding.rvCart.adapter = adapter
    }

    override fun onDelete(product: ProductCartEntity) {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Delete")
        dialog.setMessage("Are you sure want to delete this product?")
        dialog.setPositiveButton("Yes"){_,_ ->
            lifecycleScope.launch {
                viewModel.deleteProductCart(product)
                viewModel.getProductCarts(viewModel.getCurrentUser()?.uid ?: "")
            }
        }
        dialog.setNegativeButton("No"){_,_ ->
        }
        dialog.setCancelable(false)
        dialog.show()
    }

    override fun onPlus(product: List<Product>) {
            val formattedPrice = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
            formattedPrice.maximumFractionDigits = 0
            val totalPrice = product.sumOf { it.price * (it.quantity) }
            val formatRupiah = formattedPrice.format(totalPrice)
            binding.tvPrice.text = formatRupiah

    }

    override fun onMinus(product: List<Product>) {
            val formattedPrice = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
            formattedPrice.maximumFractionDigits = 0
            val totalPrice = product.sumOf { it.price * (it.quantity) }
            val formatRupiah = formattedPrice.format(totalPrice)
            binding.tvPrice.text = formatRupiah

    }
}