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

    private val tag = "My TAG"

    private var _binding: FragmentPromoListBinding? = null
    private val binding get() = _binding!!

    private val adapter = PromoAdapter()

    private val promoViewModels by viewModels<PromoViewModel> {
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

        promoViewModels.loadPromos()

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        binding.swipeRefreshLayout.setOnRefreshListener {
            promoViewModels.loadPromos()
        }


        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                promoViewModels.promoState.collect {
                    showProgress()
                    showPromos(promoList = listOf())
                    hideProgress()
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showProgress() {
        binding.progress.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        binding.progress.visibility = View.GONE
    }

     private fun showPromos(promoList: List<PromoState>) {
        binding.recyclerView.visibility = View.VISIBLE
        adapter.submitList(promoList)

         if (promoList.isEmpty()) {
             Log.e(tag, "PromoListEmpty")
             showError()
         } else
             Log.d(tag, "showPromos")


     }

    private fun hidePromos() {
        binding.recyclerView.visibility = View.GONE
    }

    private fun showError() {
        Toast.makeText(
            requireContext(),
            getString(R.string.error_wile_loading_data),
            Toast.LENGTH_SHORT
        ).show()
    }
}
