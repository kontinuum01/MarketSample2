package ru.gb.android.workshop2.presentation.list.start.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ru.gb.android.workshop2.marketsample.R
import ru.gb.android.workshop2.marketsample.databinding.FragmentProductListBinding
import ru.gb.android.workshop2.presentation.list.start.product.adapter.ProductsAdapter

class ProductListFragment : Fragment(), ProductListView {

    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!

    private val adapter = ProductsAdapter()

    private val productListPresenter: ProductListPresenter by lazy {
        FeatureServiceLocator.providePresenter()
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

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        binding.swipeRefreshLayout.setOnRefreshListener {
            productListPresenter.refresh()
        }

        productListPresenter.onViewAttached(this)
        productListPresenter.loadProduct()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        productListPresenter.dispose()
        _binding = null
    }

    override fun showProgress() {
        binding.progress.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        binding.progress.visibility = View.GONE
    }

    override fun hidePullToRefresh() {
        binding.swipeRefreshLayout.isRefreshing = false
    }

    override fun showProducts(productList: List<ProductVO>) {
        binding.recyclerView.visibility = View.VISIBLE
        adapter.submitList(productList)
    }

    override fun hideProducts() {
        binding.recyclerView.visibility = View.GONE
    }

    override fun showError() {
        Toast.makeText(
            requireContext(),
            getString(R.string.error_wile_loading_data),
            Toast.LENGTH_SHORT
        ).show()
    }
}
