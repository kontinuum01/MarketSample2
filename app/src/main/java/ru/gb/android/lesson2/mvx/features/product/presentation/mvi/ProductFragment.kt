package ru.gb.android.lesson2.mvx.features.product.presentation.mvi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import io.reactivex.functions.Consumer
import ru.gb.android.lesson2.marketsample.databinding.FragmentProductBinding
import ru.gb.android.lesson2.mvx.Injector
import ru.gb.android.lesson2.mvx.features.product.presentation.mvi.entities.ProductState
import ru.gb.android.lesson2.mvx.features.product.presentation.mvi.entities.State
import ru.gb.android.lesson2.mvx.features.product.presentation.mvi.entities.Wish

class ProductFragment : Fragment(), Consumer<State> {
    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!

    private val feature by lazy { Injector.provideFeature() }

    private val mviBindings by lazy {
        ProductFragmentBindings(
            view = this,
            feature = feature,
            newsListener = NewsListener(context = requireContext())
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mviBindings.setup(this)

        if (savedInstanceState == null) {
            feature.accept(Wish.LoadProduct)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun accept(state: State) {
        with(state) {
            when {
                isLoading -> showLoading()
                else -> showProduct(state.product)
            }
        }
    }

    private fun showLoading() {
        hideAll()
        binding.progress.visibility = View.VISIBLE
    }

    private fun showProduct(state: ProductState) {
        hideAll()
        binding.image.load(state.image)
        binding.image.visibility = View.VISIBLE

        binding.name.text = state.name
        binding.name.visibility = View.VISIBLE

        binding.price.text = "${state.price} руб"
        binding.price.visibility = View.VISIBLE

        if (state.hasDiscount) {
            binding.promo.visibility = View.VISIBLE
            binding.promo.text = "${state.discount} %"
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
