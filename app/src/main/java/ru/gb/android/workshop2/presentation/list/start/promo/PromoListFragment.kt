package ru.gb.android.workshop2.presentation.list.start.promo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ru.gb.android.workshop2.marketsample.R
import ru.gb.android.workshop2.marketsample.databinding.FragmentPromoListBinding
import ru.gb.android.workshop2.presentation.list.start.promo.adapter.PromoAdapter

class PromoListFragment : Fragment(), PromoListView {

    private var _binding: FragmentPromoListBinding? = null
    private val binding get() = _binding!!

    private val adapter = PromoAdapter()

    private val promoListPresenter: PromoListPresenter by lazy {
        FeatureServiceLocator.providePresenter()
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

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        binding.swipeRefreshLayout.setOnRefreshListener {
            promoListPresenter.refresh()
        }

        promoListPresenter.onViewAttached(this)
        promoListPresenter.loadPromos()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        promoListPresenter.dispose()
        _binding = null
    }

    override fun showProgress() {
        binding.progress.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        binding.progress.visibility = View.GONE
    }

    override fun showPromos(promoList: List<PromoVO>) {
        binding.recyclerView.visibility = View.VISIBLE
        adapter.submitList(promoList)
    }

    override fun hidePromos() {
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
