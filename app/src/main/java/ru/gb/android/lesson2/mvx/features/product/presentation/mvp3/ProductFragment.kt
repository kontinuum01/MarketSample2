package ru.gb.android.lesson2.mvx.features.product.presentation.mvp3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.gb.android.lesson2.marketsample.databinding.FragmentProductBinding
import ru.gb.android.lesson2.mvx.Injector

class ProductFragment : Fragment() {

    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!

    private val productPresenter: ProductPresenter by lazy { Injector.providePresenter3() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductBinding.inflate(inflater, container, false)

        val productView = ProductViewImpl(binding.root)
        productPresenter.onViewAttached(productView)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productPresenter.loadProduct()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        productPresenter.dispose()
        _binding = null
    }
}
