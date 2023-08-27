package ru.gb.android.lesson2.mvx.features.product.presentation.mvp2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import coil.load
import ru.gb.android.lesson2.marketsample.databinding.FragmentProductBinding
import ru.gb.android.lesson2.mvx.Injector
import ru.gb.android.lesson2.mvx.features.product.presentation.ProductVO

class ProductFragment : Fragment(), ProductView {

    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!

    private val productPresenter: ProductPresenter by lazy { Injector.providePresenter2() }

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

        binding.addToCart.setOnClickListener {
            productPresenter.addToCart()
        }
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

    override fun showProduct(product: ProductVO) {
        binding.image.load(product.image)
        binding.image.visibility = View.VISIBLE

        binding.name.text = product.name
        binding.name.visibility = View.VISIBLE

        binding.price.text = "${product.price} руб"
        binding.price.visibility = View.VISIBLE

        if (product.hasDiscount) {
            binding.promo.visibility = View.VISIBLE
            binding.promo.text = "${product.discount} %"
        } else {
            binding.promo.visibility = View.GONE
        }

        binding.addToCart.visibility = View.VISIBLE
    }

    override fun hideProduct() {
        binding.image.visibility = View.GONE
        binding.name.visibility = View.GONE
        binding.price.visibility = View.GONE
        binding.promo.visibility = View.GONE
        binding.addToCart.visibility = View.GONE
    }

    override fun showError() {
        Toast.makeText(
            requireContext(),
            "Error wile loading data",
            Toast.LENGTH_SHORT
        ).show()
    }
}
