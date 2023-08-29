package ru.gb.android.workshop2.presentation.card.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import coil.load
import ru.gb.android.workshop2.presentation.common.DiscountFormatter
import ru.gb.android.workshop2.presentation.common.PriceFormatter
import ru.gb.android.workshop2.marketsample.R
import ru.gb.android.workshop2.marketsample.databinding.FragmentProductBinding

class ProductFragment : Fragment(), ProductView {

    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!

    private val productPresenter: ProductPresenter by lazy {
        FeatureServiceLocator.providePresenter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        productPresenter.onViewAttached(this)
        productPresenter.loadProduct()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        productPresenter.dispose()
        _binding = null
    }

    override fun showProgress() {
        binding.progress.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        binding.progress.visibility = View.GONE
    }

    override fun showImage(image: String) {
        binding.image.load(image)
        binding.image.visibility = View.VISIBLE
    }

    override fun hideImage() {
        binding.image.visibility = View.GONE
    }

    override fun showName(name: String) {
        binding.name.text = name
        binding.name.visibility = View.VISIBLE
    }

    override fun hideName() {
        binding.name.visibility = View.GONE
    }

    override fun showPrice(price: String) {
        binding.price.text = getString(R.string.price_with_arg, price)
        binding.price.visibility = View.VISIBLE
    }

    override fun hidePrice() {
        binding.price.visibility = View.GONE
    }

    override fun showPromo(discount: String) {
        binding.promo.visibility = View.VISIBLE
        binding.promo.text = discount
    }

    override fun hidePromo() {
        binding.promo.visibility = View.GONE
    }

    override fun showAddToCart() {
        binding.addToCart.visibility = View.VISIBLE
    }

    override fun hideAddToCart() {
        binding.addToCart.visibility = View.GONE
    }

    override fun showError() {
        Toast.makeText(
            requireContext(),
            getString(R.string.error_wile_loading_data),
            Toast.LENGTH_SHORT
        ).show()
    }
}
