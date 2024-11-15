package com.example.shopease.presentation.orderHistory

import android.os.Bundle
import android.util.Log
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
import kotlinx.coroutines.launch

class OrderFragment : Fragment() {
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
        initData()
    }

    private fun initData() {
        lifecycleScope.launch {
            viewModel.orderHistoryState.collect{value ->
                when(value){
                    is OrderHistoryState.Error -> {
                        binding?.shimmerLayout?.stopShimmer()
                        binding?.shimmerLayout?.visibility = View.GONE
                        binding?.rvOrder?.visibility = View.VISIBLE
                        Toast.makeText(requireContext(),value.message,Toast.LENGTH_SHORT).show()
                    }
                    OrderHistoryState.Loading -> {
                        binding?.shimmerLayout?.startShimmer()
                        binding?.shimmerLayout?.visibility = View.VISIBLE
                        binding?.rvOrder?.visibility = View.GONE
                    }
                    is OrderHistoryState.Success -> {
                        binding?.shimmerLayout?.stopShimmer()
                        binding?.shimmerLayout?.visibility = View.GONE
                        if (value.data.isEmpty()){
                            binding?.apply {
                                ivOrderEmpty.visibility = View.VISIBLE
                                tvEmpty.visibility = View.VISIBLE
                                rvOrder.visibility = View.GONE
                            }
                        } else{
                            binding?.apply {
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

    private fun showData(listOrder: List<OrderHistory>){
        val adapter = OrderAdapter(listOrder)
        binding?.apply {
            rvOrder.layoutManager = LinearLayoutManager(requireContext())
            rvOrder.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}