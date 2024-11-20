package com.example.shopease.presentation.orderHistoryDetail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shopease.databinding.ActivityOrderDetailBinding
import com.example.shopease.domain.model.OrderDetailHistoryState
import com.example.shopease.domain.model.OrderHistory
import com.example.shopease.presentation.adapter.OrderDetailAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class OrderDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderDetailBinding
    private val viewModel: OrderDetailViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val orderId = intent.getStringExtra(ORDER_ID)
        binding.ivBack.setOnClickListener {
            finish()
        }
        if (orderId != null) {
            viewModel.getOrderHistoryDetail(orderId)
        }
        lifecycleScope.launch {
            viewModel.orderDetailState.collect { value ->
                when (value) {
                    is OrderDetailHistoryState.Error -> {
                        binding.shimmerLayout.stopShimmer()
                        binding.shimmerLayout.visibility = View.GONE
                        binding.rvDetailOrder.visibility = View.GONE
                        Toast.makeText(this@OrderDetailActivity, value.message, Toast.LENGTH_SHORT)
                            .show()
                    }

                    OrderDetailHistoryState.Loading -> {
                        binding.shimmerLayout.startShimmer()
                        binding.shimmerLayout.visibility = View.VISIBLE
                        binding.rvDetailOrder.visibility = View.GONE

                    }

                    is OrderDetailHistoryState.Success -> {
                        binding.shimmerLayout.stopShimmer()
                        binding.shimmerLayout.visibility = View.GONE
                        binding.rvDetailOrder.visibility = View.VISIBLE
                        showData(value.data)
                    }
                }
            }
        }

    }

    private fun showData(listOrder: List<OrderHistory.OrderDetail>) {
        val adapter = OrderDetailAdapter(listOrder)
        binding.apply {
            rvDetailOrder.layoutManager = LinearLayoutManager(this@OrderDetailActivity)
            rvDetailOrder.adapter = adapter
        }
    }

    companion object {
        const val ORDER_ID = "order_id"

    }
}