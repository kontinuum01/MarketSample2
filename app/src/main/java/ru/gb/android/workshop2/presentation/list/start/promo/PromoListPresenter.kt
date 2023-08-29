package ru.gb.android.workshop2.presentation.list.start.promo

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import ru.gb.android.workshop2.domain.promo.ConsumePromosUseCase

class PromoListPresenter(
    private val promoVOMapper: PromoVOMapper,
    private val consumePromosUseCase: ConsumePromosUseCase,
) {
    private lateinit var coroutineScope : CoroutineScope

    private var _view: PromoListView? = null
    private val view: PromoListView
        get() = _view!!

    fun onViewAttached(view: PromoListView) {
        _view = view
        coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    }

    fun loadPromos() {
        consumePromosUseCase()
            .map { promos ->
                promos.map(promoVOMapper::map)
            }
            .onStart {
                view.showProgress()
                view.hidePromos()
            }
            .onEach { promoVOList ->
                view.hideProgress()
                view.showPromos(promoVOList)
            }
            .catch {
                view.showError()
            }
            .launchIn(coroutineScope)
    }

    fun dispose() {
        coroutineScope.cancel()
    }

    fun refresh() {
        loadPromos()
    }
}
