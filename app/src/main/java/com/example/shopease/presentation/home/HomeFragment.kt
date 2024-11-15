package com.example.shopease.presentation.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.SearchView.OnCloseListener
import android.widget.SearchView.OnQueryTextListener
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.shopease.databinding.FragmentHomeBinding
import com.example.shopease.domain.model.Product
import com.example.shopease.domain.model.ProductState
import com.example.shopease.presentation.cart.CartActivity
import com.example.shopease.presentation.SearchActivity
import com.example.shopease.presentation.detailProduct.DetailProductActivity
import com.example.shopease.presentation.detailProduct.DetailProductActivity.Companion.EXTRA_ID
import com.example.shopease.presentation.adapter.ItemListener
import com.example.shopease.presentation.adapter.ProductAdapter
import kotlinx.coroutines.launch


class HomeFragment : Fragment(),ItemListener {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding

    private val viewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.cvCart?.setOnClickListener {
            startActivity(Intent(requireActivity(), CartActivity::class.java))
        }

        binding?.svProduct?.setOnQueryTextListener(object :OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.getProductsBySearch(query.toString())
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.toString().isEmpty()){
                    viewModel.getProducts()
                }
                return false
            }
        })
        val clearButton = binding?.svProduct?.findViewById<SearchView>(androidx.appcompat.R.id.search_close_btn)
        clearButton?.setOnClickListener {
            binding?.svProduct?.setQuery("", false)
            viewModel.getProducts()
        }
        binding?.svProduct?.setOnCloseListener(object :OnCloseListener{
            override fun onClose(): Boolean {
                viewModel.getProducts()
                return false
            }
        })
        lifecycleScope.launch {
            viewModel.productState.collect { value ->
                when (value) {
                    is ProductState.Error -> {
                        binding?.shimmerLayout?.stopShimmer()
                        binding?.shimmerLayout?.visibility = View.GONE
                        binding?.rvProduct?.visibility = View.VISIBLE
                        Toast.makeText(requireContext(), value.message, Toast.LENGTH_SHORT).show()
                    }

                    ProductState.Loading -> {
                        binding?.shimmerLayout?.startShimmer()
                        binding?.shimmerLayout?.visibility = View.VISIBLE
                        binding?.rvProduct?.visibility = View.GONE

                    }
                    is ProductState.Success -> {
                        binding?.shimmerLayout?.stopShimmer()
                        binding?.shimmerLayout?.visibility = View.GONE
                        binding?.rvProduct?.visibility = View.VISIBLE
                        val data = value.data
                        showData(data)
                    }
                }
            }
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showData(listProduct: List<Product>) {
        val adapter = ProductAdapter(listProduct,this)
        binding?.apply {
            rvProduct.layoutManager = GridLayoutManager(requireContext(), 2)
            rvProduct.adapter = adapter

        }
    }

    override fun onItemClick(id: Int) {
        val intent = Intent(requireContext(), DetailProductActivity::class.java)
        intent.putExtra(EXTRA_ID, id)
        startActivity(intent)
    }
}