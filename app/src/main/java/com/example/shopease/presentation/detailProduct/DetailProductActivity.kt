package com.example.shopease.presentation.detailProduct

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.shopease.data.model.ProductCartEntity
import com.example.shopease.data.model.ProductFavoriteEntity
import com.example.shopease.databinding.ActivityDetailProductBinding
import com.example.shopease.databinding.DialogCheckoutBinding
import com.example.shopease.databinding.LoadingDialogBinding
import com.example.shopease.domain.model.Product
import com.example.shopease.domain.model.ProductDetailState
import com.example.shopease.presentation.checkout.CheckOutActivity
import com.example.shopease.presentation.checkout.CheckOutActivity.Companion.EXTRA_PRODUCT
import com.example.shopease.presentation.adapter.ImageSliderAdapter
import com.example.shopease.utils.ShopHelper.formatPrice
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailProductBinding
    private val viewModel: DetailProductViewModel by viewModels()
    private val loadingDialog by lazy {
        val binding = LoadingDialogBinding.inflate(layoutInflater)
        AlertDialog.Builder(this)
            .setView(binding.root)
            .setCancelable(false)
            .create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadingDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        binding.ivBack.setOnClickListener {
            finish()
        }
        val id = intent.getIntExtra(EXTRA_ID, 0)
        viewModel.getDetailProduct(id)
        lifecycleScope.launch {
            viewModel.productDetailState.collect { value ->
                when (value) {
                    is ProductDetailState.Error -> {
                        loadingDialog.dismiss()
                        Toast.makeText(
                            this@DetailProductActivity,
                            value.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    ProductDetailState.Loading -> {
                        loadingDialog.show()
                    }

                    is ProductDetailState.Success -> {
                        loadingDialog.dismiss()
                        val dataProduct = value.data
                        val isFavorite = viewModel.isFavorite(
                            dataProduct.id,
                            viewModel.getCurrentUser()?.uid ?: ""
                        )
                        if (isFavorite) {
                            binding.ivFavorite.setColorFilter(Color.RED)
                        } else {
                            binding.ivFavorite.colorFilter = null
                        }
                        initData(dataProduct)
                        binding.btnCheckout.setOnClickListener {
                            showCheckoutDialog(dataProduct)
                        }
                        binding.ivFavorite.setOnClickListener {
                            insertFavorite(dataProduct)
                        }
                        binding.btnCart.setOnClickListener {
                            addProductCart(dataProduct)
                            Toast.makeText(
                                this@DetailProductActivity,
                                "Product added to cart",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        binding.ivShare.setOnClickListener {
                            val data = listOf(dataProduct.image[0], dataProduct.title, dataProduct.price)
                            val product = data.joinToString { "$it\n" }
                            val sendIntent: Intent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_TEXT, product)
                                type = "text/plain"
                            }
                            val shareIntent = Intent.createChooser(sendIntent, null)
                            startActivity(shareIntent)
                        }
                    }

                }
            }
        }

    }

    private fun addProductCart(product: Product) {
        viewModel.insertProductCart(
            ProductCartEntity(
                productId = product.id,
                productName = product.title,
                productPrice = product.price,
                productDescription = product.description,
                productCategory = product.category,
                productImage = product.image[0],
                productRating = product.rating,
                userId = viewModel.getCurrentUser()?.uid ?: ""
            )
        )
    }


    private fun insertFavorite(dataProduct: Product) {
        if (binding.ivFavorite.colorFilter == null) {
            binding.ivFavorite.setColorFilter(Color.RED)
            viewModel.insertFavorite(
                ProductFavoriteEntity(
                    productId = dataProduct.id,
                    productName = dataProduct.title,
                    productPrice = dataProduct.price,
                    productDescription = dataProduct.description,
                    productCategory = dataProduct.category,
                    productImage = dataProduct.image[0],
                    productRating = dataProduct.rating,
                    userId = viewModel.getCurrentUser()?.uid ?: "",
                    isFavorite = true
                )
            )
            Toast.makeText(this@DetailProductActivity, "Favorite added", Toast.LENGTH_SHORT).show()
        } else {
            binding.ivFavorite.colorFilter = null
            viewModel.deleteFavorite(
                ProductFavoriteEntity(
                    productId = dataProduct.id,
                    productName = dataProduct.title,
                    productPrice = dataProduct.price,
                    productDescription = dataProduct.description,
                    productCategory = dataProduct.category,
                    productImage = dataProduct.image[0],
                    productRating = dataProduct.rating,
                    userId = viewModel.getCurrentUser()?.uid ?: "",
                    isFavorite = false
                )
            )
            Toast.makeText(this@DetailProductActivity, "Favorite removed", Toast.LENGTH_SHORT)
                .show()
        }
    }


    private fun showCheckoutDialog(dataProduct: Product) {
        val dialogBinding = DialogCheckoutBinding.inflate(layoutInflater)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(dialogBinding.root)
        dialog.show()
        dialogBinding.apply {
            Glide.with(this@DetailProductActivity)
                .load(dataProduct.image[0])
                .into(ivProduct)
            tvNameProduct.text = dataProduct.title
            var quantity = tvQuantity.text.toString().toInt()
            var price = 0
            fun updatePrice() {
                price = dataProduct.price * quantity
                tvPriceProduct.text = formatPrice(price)
            }
            updatePrice()
            ivMin.setOnClickListener {
                if (quantity > 1) {
                    quantity -= 1
                    tvQuantity.text = quantity.toString()
                    updatePrice()
                }
            }
            ivAdd.setOnClickListener {
                quantity += 1
                tvQuantity.text = quantity.toString()
                updatePrice()
            }
            btnCheckoutDialog.setOnClickListener {
                val product = Product(
                    id = dataProduct.id,
                    title = dataProduct.title,
                    price = dataProduct.price,
                    description = dataProduct.description,
                    category = dataProduct.category,
                    image = dataProduct.image,
                    quantity = quantity,
                    rating = dataProduct.rating,
                    totalPrice = price
                )
                val intent = Intent(this@DetailProductActivity, CheckOutActivity::class.java)
                intent.putExtra(EXTRA_PRODUCT, product)
                startActivity(intent)
                dialog.dismiss()
            }

        }
    }

    private fun initData(dataProduct: Product) {
        binding.apply {
            tvDetailName.text = dataProduct.title
            tvPriceDetail.text = formatPrice(dataProduct.price)
            tvDescriptionProduct.text = dataProduct.description
            rateProduct.rating = dataProduct.rating
            tvRate.text = dataProduct.rating.toString()
            val imageAdapter = ImageSliderAdapter(dataProduct.image)
            vpDetailProductImages.adapter = imageAdapter

            val dotsIndicator = binding.dotsIndicator
            dotsIndicator.setViewPager2(vpDetailProductImages)
        }
    }

    companion object {
        const val EXTRA_ID = "extra_id"

    }
}