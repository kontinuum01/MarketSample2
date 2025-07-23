package ru.gb.android.workshop2.presentation.list.promo

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
import ru.gb.android.workshop2.marketsample.databinding.FragmentPromoListBinding
import ru.gb.android.workshop2.presentation.list.promo.adapter.PromoAdapter

class PromoListFragment : Fragment() {

    private var _binding: FragmentPromoListBinding? = null
    private val binding get() = _binding!!

    private val adapter = PromoAdapter()

    private val promoViewModel by viewModels<PromoViewModel> {
        FeatureServiceLocator.providePromoViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPromoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        promoViewModel.loadPromos()

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        binding.swipeRefreshLayout.setOnRefreshListener {
            promoViewModel.loadPromos()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                promoViewModel.promoState.collect { state ->
                    when {
                        state.isLoading -> renderLoading()
                        state.hasError -> {
                            showError()
                            promoViewModel.errorShown()
                        }

                        else -> renderPromoListState(state.promoList)
                    }
                }
            }
        }
    }

    private fun renderPromoListState(promoList: List<PromoState>) {
        binding.recyclerView.visibility = View.VISIBLE
        binding.swipeRefreshLayout.isRefreshing = false
        showPromoList(promoList)
        Log.i("PromoListFragment", "renderPromoListState")
    }

    private fun renderLoading(){
        hideAll()
        binding.progress.visibility = View.VISIBLE
        Log.i("My tag","renderLoading")
    }

    private fun showPromoList(promoList: List<PromoState>) {
        binding.recyclerView.visibility = View.VISIBLE
        adapter.submitList(promoList)
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
