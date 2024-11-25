package com.example.shopease.presentation.search

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.shopease.databinding.ActivitySearchBinding
import com.example.shopease.domain.model.Product
import com.example.shopease.domain.model.ProductState
import com.example.shopease.presentation.adapter.ItemListener
import com.example.shopease.presentation.adapter.ProductAdapter
import com.example.shopease.presentation.detailProduct.DetailProductActivity
import com.example.shopease.presentation.detailProduct.DetailProductActivity.Companion.EXTRA_ID
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchActivity : AppCompatActivity(), ItemListener {
    private lateinit var binding: ActivitySearchBinding
    private val viewModel: SearchViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.apply {
            svProduct.apply {
                isFocusable = true
                isIconified = false
            }

            ivBack.setOnClickListener {
                finish()
            }
            svProduct.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    viewModel.getProductsBySearch(query.toString())
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText.toString().isEmpty()) {
                        viewModel.getProducts()
                    }
                    return false
                }
            })

            lifecycleScope.launch {
                viewModel.productState.collect { value ->
                    when (value) {
                        is ProductState.Error -> {
                            shimmerLayout.stopShimmer()
                            shimmerLayout.visibility = View.GONE
                            rvSearch.visibility = View.VISIBLE
                            Toast.makeText(this@SearchActivity, value.message, Toast.LENGTH_SHORT)
                                .show()
                        }

                        ProductState.Loading -> {
                            shimmerLayout.startShimmer()
                            shimmerLayout.visibility = View.VISIBLE
                            rvSearch.visibility = View.GONE
                            ivSearchActivity.visibility = View.GONE
                            tvEmpty.visibility = View.GONE

                        }

                        is ProductState.Success -> {
                            val data = value.data
                            shimmerLayout.stopShimmer()
                            if (data.isEmpty()){
                                shimmerLayout.visibility = View.GONE
                                rvSearch.visibility = View.GONE
                                ivSearchActivity.visibility = View.VISIBLE
                                tvEmpty.visibility = View.VISIBLE
                            }else{
                                shimmerLayout.visibility = View.GONE
                                rvSearch.visibility = View.VISIBLE
                                ivSearchActivity.visibility = View.GONE
                                tvEmpty.visibility = View.GONE
                                showData(data)
                            }
                        }
                    }
                }
            }
        }

    }


    private fun showData(listProduct: List<Product>) {
        val adapter = ProductAdapter(listProduct, this)
        binding.apply {
            rvSearch.layoutManager = GridLayoutManager(this@SearchActivity, 2)
            rvSearch.adapter = adapter

        }
    }

    override fun onItemClick(id: Int) {
        val intent = Intent(this, DetailProductActivity::class.java)
        intent.putExtra(EXTRA_ID, id)
        startActivity(intent)
    }
}