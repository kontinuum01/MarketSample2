package ru.gb.android.workshop2.presentation.list.product

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import ru.gb.android.workshop2.marketsample.R
import ru.gb.android.workshop2.marketsample.databinding.FragmentProductListBinding
import ru.gb.android.workshop2.presentation.list.product.adapter.ProductsAdapter

class ProductListFragment : Fragment() {

    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("Binding should not be accessed after onDestroyView")

    private val adapter = ProductsAdapter()

    private val viewModel by viewModels<ProductListViewModel> {
        FeatureServiceLocator.provideProductListViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadProduct()

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.loadProduct()

        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    when {
                        state.isLoading -> renderLoading()
                        state.hasError -> {
                            showError()
                            viewModel.errorShown()
                        }

                        else -> renderProductListState(state.productList)
                    }
                }
            }
        }
    }

     private fun renderProductListState(productList: List<ProductListState>) {
        binding.recyclerView.visibility = View.VISIBLE
        binding.swipeRefreshLayout.isRefreshing = false
        showProductList(productList)
        Log.i("ProductListFragment", "renderProductListState")
    }

    private fun renderLoading(){
        hideAll()
        binding.progress.visibility = View.VISIBLE
        Log.i("My tag","renderLoading")
    }

    private fun showProductList(productList: List<ProductListState>) {
        binding.recyclerView.visibility = View.VISIBLE
        adapter.submitList(productList)
        binding.progress.visibility = View.GONE
    }

    private fun hideAll() {
        binding.swipeRefreshLayout.isRefreshing = false
        binding.recyclerView.visibility = View.GONE
        binding.progress.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }

    private fun showError() {
        Toast.makeText(
            requireContext(),
            getString(R.string.error_wile_loading_data),
            Toast.LENGTH_SHORT
        ).show()
    }
}
