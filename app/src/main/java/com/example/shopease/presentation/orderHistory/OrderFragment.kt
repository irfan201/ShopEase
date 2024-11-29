package com.example.shopease.presentation.orderHistory

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shopease.databinding.FragmentOrderBinding
import com.example.shopease.domain.model.OrderHistory
import com.example.shopease.domain.model.OrderHistoryState
import com.example.shopease.presentation.adapter.OrderAdapter
import com.example.shopease.presentation.adapter.OrderListener
import com.example.shopease.presentation.orderHistoryDetail.OrderDetailActivity
import com.example.shopease.presentation.orderHistoryDetail.OrderDetailActivity.Companion.ORDER_ID
import kotlinx.coroutines.launch

class OrderFragment : Fragment(), OrderListener {
    private var _binding : FragmentOrderBinding? = null
    private val binding get() = _binding

    private val viewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOrderBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.swipeRefresh?.setOnRefreshListener {
            viewModel.getOrderHistory(viewModel.getCurrenUser()?.email ?: "test@gmail.com")
            initData()
        }
        initData()
    }

    private fun initData() {
        binding?.apply {
            lifecycleScope.launch {
                viewModel.orderHistoryState.collect{value ->
                    when(value){
                        is OrderHistoryState.Error -> {
                           shimmerLayout.stopShimmer()
                            shimmerLayout.visibility = View.GONE
                            rvOrder.visibility = View.GONE
                            ivNoInternet.visibility = View.VISIBLE
                            tvNoInternet.visibility = View.VISIBLE
                            cvRefresh.visibility = View.VISIBLE
                            cvRefresh.setOnClickListener {
                                viewModel.getOrderHistory(viewModel.getCurrenUser()?.email ?: "test@gmail.com")
                            }
                            Toast.makeText(requireContext(),value.message,Toast.LENGTH_SHORT).show()
                        }
                        OrderHistoryState.Loading -> {
                            shimmerLayout.startShimmer()
                            shimmerLayout.visibility = View.VISIBLE
                            rvOrder.visibility = View.GONE
                            ivNoInternet.visibility = View.GONE
                            tvNoInternet.visibility = View.GONE
                            cvRefresh.visibility = View.GONE
                        }
                        is OrderHistoryState.Success -> {
                            swipeRefresh.isRefreshing = false
                            shimmerLayout.stopShimmer()
                            shimmerLayout.visibility = View.GONE
                            ivNoInternet.visibility = View.GONE
                            tvNoInternet.visibility = View.GONE
                            cvRefresh.visibility = View.GONE
                            if (value.data.isEmpty()){
                                apply {
                                    ivOrderEmpty.visibility = View.VISIBLE
                                    tvEmpty.visibility = View.VISIBLE
                                    rvOrder.visibility = View.GONE

                                }
                            } else{
                                apply {
                                    ivOrderEmpty.visibility = View.GONE
                                    tvEmpty.visibility = View.GONE
                                    rvOrder.visibility = View.VISIBLE

                                }
                                showData(value.data)
                            }

                        }
                    }
                }
            }
        }
        
    }

    private fun showData(listOrder: List<OrderHistory>){
        val adapter = OrderAdapter(listOrder,this)
        binding?.apply {
            rvOrder.layoutManager = LinearLayoutManager(requireContext())
            rvOrder.adapter = adapter
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(id: String) {
        val intent = Intent(requireActivity(), OrderDetailActivity::class.java)
        intent.putExtra(ORDER_ID, id)
        startActivity(intent)
    }


}