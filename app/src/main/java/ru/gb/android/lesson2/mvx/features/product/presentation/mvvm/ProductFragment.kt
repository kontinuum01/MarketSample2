package ru.gb.android.lesson2.mvx.features.product.presentation.mvvm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.gb.android.lesson2.marketsample.databinding.FragmentProductBinding
import ru.gb.android.lesson2.mvx.Injector

class ProductFragment : Fragment() {

    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProductsViewModel by viewModels(
        factoryProducer = { Injector.provideViewModelFactory() }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeUI()
    }

    private fun subscribeUI() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.state.collect { state ->
                        when {
                            state.isLoading -> showLoading()
                            else -> showProduct(productState = state)
                        }
                    }
                }

                launch {
                    viewModel.isError
                        .filter { isError -> isError }
                        .onEach {
                            Toast.makeText(
                                requireContext(),
                                "Error wile loading data",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        .collect()
                }
            }
        }
    }

    private fun showLoading() {
        hideAll()
        binding.progress.visibility = View.VISIBLE
    }

    private fun showProduct(productState: ProductState) {
        hideAll()
        binding.image.load(productState.image)
        binding.image.visibility = View.VISIBLE

        binding.name.text = productState.name
        binding.name.visibility = View.VISIBLE

        binding.price.text = "${productState.price} руб"
        binding.price.visibility = View.VISIBLE

        if (productState.hasDiscount) {
            binding.promo.visibility = View.VISIBLE
            binding.promo.text = "${productState.discount} %"
        } else {
            binding.promo.visibility = View.GONE
        }

        binding.addToCart.visibility = View.VISIBLE
    }

    private fun hideAll() {
        binding.progress.visibility = View.GONE
        binding.image.visibility = View.GONE
        binding.name.visibility = View.GONE
        binding.promo.visibility = View.GONE
        binding.price.visibility = View.GONE
        binding.progress.visibility = View.GONE
        binding.addToCart.visibility = View.GONE
    }
}
