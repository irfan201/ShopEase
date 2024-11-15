package com.example.shopease.presentation.category

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.shopease.R
import com.example.shopease.databinding.FragmentCategoryBinding
import com.example.shopease.domain.model.Product
import com.example.shopease.domain.model.ProductState
import com.example.shopease.presentation.adapter.ItemListener
import com.example.shopease.presentation.adapter.ProductAdapter
import com.example.shopease.presentation.detailProduct.DetailProductActivity
import kotlinx.coroutines.launch


class CategoryFragment : Fragment(), ItemListener {
    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding

    private val viewModel: CategoryViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            lifecycleScope.launch {
                if (viewModel.getCategory() != null) {
                    viewModel.getProductsbyCategory(viewModel.getCategory()!!)
                    when (viewModel.getCategory()) {
                        "SPORT" -> setCardBackgroundColor(cvSport)
                        "CASUAL" -> setCardBackgroundColor(cvCasual)
                        "FORMAL" -> setCardBackgroundColor(cvFormal)
                    }
                }
            }
            cvSport.setOnClickListener {
                lifecycleScope.launch {
                    viewModel.getProductsbyCategory("SPORT")
                }
                viewModel.saveCategory("SPORT")
                setCardBackgroundColor(cvSport)
                removeCardBackgroundColor(cvCasual)
                removeCardBackgroundColor(cvFormal)
            }
            cvCasual.setOnClickListener {
                lifecycleScope.launch {
                    viewModel.getProductsbyCategory("CASUAL")
                }
                viewModel.saveCategory("CASUAL")
                setCardBackgroundColor(cvCasual)
                removeCardBackgroundColor(cvSport)
                removeCardBackgroundColor(cvFormal)
            }
            cvFormal.setOnClickListener {
                lifecycleScope.launch {
                    viewModel.getProductsbyCategory("FORMAL")
                }
                viewModel.saveCategory("FORMAL")
                setCardBackgroundColor(cvFormal)
                removeCardBackgroundColor(cvSport)
                removeCardBackgroundColor(cvCasual)
            }
        }
        initData()
    }

    private fun initData() {
        lifecycleScope.launch {
            viewModel.productState.collect { value ->
                when (value) {
                    is ProductState.Error -> {
                        binding?.shimmerLayout?.stopShimmer()
                        binding?.shimmerLayout?.visibility = View.GONE
                        binding?.rvCategory?.visibility = View.VISIBLE
                        Toast.makeText(requireContext(), value.message, Toast.LENGTH_SHORT).show()
                    }

                    ProductState.Loading -> {
                        binding?.shimmerLayout?.startShimmer()
                        binding?.shimmerLayout?.visibility = View.VISIBLE
                        binding?.rvCategory?.visibility = View.GONE
                    }

                    is ProductState.Success -> {
                        binding?.shimmerLayout?.stopShimmer()
                        binding?.shimmerLayout?.visibility = View.GONE
                        binding?.rvCategory?.visibility = View.VISIBLE
                        val data = value.data
                        showRecyclerView(data)
                    }
                }
            }
        }

    }

    private fun showRecyclerView(listProduct: List<Product>) {
        val adapter = ProductAdapter(listProduct, this)
        binding?.rvCategory?.layoutManager = GridLayoutManager(requireContext(), 2)
        binding?.rvCategory?.adapter = adapter
    }

    private fun setCardBackgroundColor(cardView: CardView) {
        cardView.setCardBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.primary_color
            )
        )
    }

    private fun removeCardBackgroundColor(cardView: CardView) {
        cardView.setCardBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.white
            )
        )
    }

    private fun categorySelected(cardView: CardView, category: String) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(id: Int) {
        val intent = Intent(requireActivity(), DetailProductActivity::class.java)
        intent.putExtra(DetailProductActivity.EXTRA_ID, id)
        startActivity(intent)
    }


}