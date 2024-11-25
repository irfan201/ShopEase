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
        if (orderId != null) {
            viewModel.getOrderHistoryDetail(orderId)
        }
        binding.apply {
            ivBack.setOnClickListener {
                finish()
            }

            lifecycleScope.launch {
                viewModel.orderDetailState.collect { value ->
                    when (value) {
                        is OrderDetailHistoryState.Error -> {
                            shimmerLayout.stopShimmer()
                            shimmerLayout.visibility = View.GONE
                            rvDetailOrder.visibility = View.GONE
                            ivNoInternet.visibility = View.VISIBLE
                            tvNoInternet.visibility = View.VISIBLE
                            cvRefresh.visibility = View.VISIBLE
                            cvRefresh.setOnClickListener {
                                if (orderId != null) {
                                    viewModel.getOrderHistoryDetail(orderId)
                                }
                            }
                            Toast.makeText(this@OrderDetailActivity, value.message, Toast.LENGTH_SHORT)
                                .show()
                        }

                        OrderDetailHistoryState.Loading -> {
                            shimmerLayout.startShimmer()
                            shimmerLayout.visibility = View.VISIBLE
                            rvDetailOrder.visibility = View.GONE
                            ivNoInternet.visibility = View.GONE
                            tvNoInternet.visibility = View.GONE
                            cvRefresh.visibility = View.GONE

                        }

                        is OrderDetailHistoryState.Success -> {
                            shimmerLayout.stopShimmer()
                            shimmerLayout.visibility = View.GONE
                            rvDetailOrder.visibility = View.VISIBLE
                            showData(value.data)
                        }
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