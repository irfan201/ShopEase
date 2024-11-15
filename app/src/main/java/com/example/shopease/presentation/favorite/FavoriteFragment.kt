package com.example.shopease.presentation.favorite

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
import com.example.shopease.databinding.FragmentFavoriteBinding
import com.example.shopease.domain.model.Product
import com.example.shopease.domain.model.ProductState
import com.example.shopease.presentation.adapter.FavoriteAdapter
import com.example.shopease.presentation.adapter.ItemListener
import com.example.shopease.presentation.detailProduct.DetailProductActivity
import kotlinx.coroutines.launch


class FavoriteFragment : Fragment(), ItemListener {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding
    private val viewModel: FavoriteViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getFavorites(viewModel.getCurrentUser()?.uid ?: "")
        lifecycleScope.launch {
            viewModel.productState.collect { value ->
                when (value) {
                    is ProductState.Error -> {
                        binding?.apply {
                            rvFavorite.visibility = View.GONE
                            ivFavoriteEmpty.visibility = View.VISIBLE
                            tvEmpty.visibility = View.VISIBLE
                        }
                        Toast.makeText(requireContext(), value.message, Toast.LENGTH_SHORT).show()
                    }

                    ProductState.Loading -> {
                    }

                    is ProductState.Success -> {
                        val data = value.data
                        if (data.isEmpty()) {
                            binding?.apply {
                                rvFavorite.visibility = View.GONE
                                ivFavoriteEmpty.visibility = View.VISIBLE
                                tvEmpty.visibility = View.VISIBLE
                            }
                        } else {
                            binding?.apply {
                                rvFavorite.visibility = View.VISIBLE
                                ivFavoriteEmpty.visibility = View.GONE
                                tvEmpty.visibility = View.GONE
                            }
                            showData(data)
                        }

                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getFavorites(viewModel.getCurrentUser()?.uid ?: "")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showData(listProduct: List<Product>) {
        val adapter = FavoriteAdapter(listProduct, this)
        binding?.apply {
            rvFavorite.layoutManager = LinearLayoutManager(requireContext())
            rvFavorite.adapter = adapter
        }
    }

    override fun onItemClick(id: Int) {
        val intent = Intent(requireActivity(), DetailProductActivity::class.java)
        intent.putExtra(DetailProductActivity.EXTRA_ID, id)
        startActivity(intent)
    }


}