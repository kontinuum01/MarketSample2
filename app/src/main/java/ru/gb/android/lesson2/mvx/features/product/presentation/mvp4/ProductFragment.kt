package ru.gb.android.lesson2.mvx.features.product.presentation.mvp4

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.gb.android.lesson2.marketsample.databinding.FragmentProductWithCustomViewBinding
import ru.gb.android.lesson2.mvx.Injector

class ProductFragment : Fragment() {

    private var _binding: FragmentProductWithCustomViewBinding? = null
    private val binding get() = _binding!!

    private val productPresenter: ProductPresenter by lazy { Injector.providePresenter4() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductWithCustomViewBinding.inflate(inflater, container, false)
        binding.productView.onViewAttached(productPresenter)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
