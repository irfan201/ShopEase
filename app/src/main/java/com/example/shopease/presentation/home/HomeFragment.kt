package com.example.shopease.presentation.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.shopease.R
import com.example.shopease.databinding.FragmentHomeBinding
import com.example.shopease.domain.model.Product
import com.example.shopease.domain.model.ProductState
import com.example.shopease.presentation.cart.CartActivity
import com.example.shopease.presentation.search.SearchActivity
import com.example.shopease.presentation.detailProduct.DetailProductActivity
import com.example.shopease.presentation.detailProduct.DetailProductActivity.Companion.EXTRA_ID
import com.example.shopease.presentation.adapter.ItemListener
import com.example.shopease.presentation.adapter.ProductAdapter
import kotlinx.coroutines.launch


class HomeFragment : Fragment(), ItemListener {
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

        binding?.apply {
            val category = viewModel.getCategory()
            when(category){
                "SPORT" -> cgCategory.check(R.id.ch_sport)
                "CASUAL" -> cgCategory.check(R.id.ch_casual)
                "FORMAL" -> cgCategory.check(R.id.ch_formal)
                else -> cgCategory.check(R.id.ch_all)
            }
            cvCart.setOnClickListener {
                startActivity(Intent(requireActivity(), CartActivity::class.java))
            }

            svProduct.setOnClickListener {
                startActivity(Intent(requireActivity(), SearchActivity::class.java))
            }


            cgCategory.setOnCheckedStateChangeListener { group, checkedIds ->
                when {
                    checkedIds.contains(R.id.ch_all)-> {
                        viewModel.getProducts()
                        viewModel.clearCategory()
                    }
                    checkedIds.contains(R.id.ch_sport) -> {
                        viewModel.getProductsbyCategory("SPORT")
                        viewModel.saveCategory("SPORT")
                    }
                    checkedIds.contains(R.id.ch_casual) -> {
                        viewModel.getProductsbyCategory("CASUAL")
                        viewModel.saveCategory("CASUAL")
                    }
                    checkedIds.contains(R.id.ch_formal) -> {
                        viewModel.getProductsbyCategory("FORMAL")
                        viewModel.saveCategory("FORMAL")
                    }
                }
            }
            lifecycleScope.launch {
                viewModel.productState.collect { value ->
                    when (value) {
                        is ProductState.Error -> {
                            shimmerLayout.stopShimmer()
                            shimmerLayout.visibility = View.GONE
                            rvProduct.visibility = View.VISIBLE
                            Toast.makeText(requireContext(), value.message, Toast.LENGTH_SHORT)
                                .show()
                        }

                        ProductState.Loading -> {
                            shimmerLayout.startShimmer()
                            shimmerLayout.visibility = View.VISIBLE
                            rvProduct.visibility = View.GONE

                        }

                        is ProductState.Success -> {
                            shimmerLayout.stopShimmer()
                            shimmerLayout.visibility = View.GONE
                            rvProduct.visibility = View.VISIBLE
                            val data = value.data
                            showData(data)
                        }
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
        val adapter = ProductAdapter(listProduct, this)
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