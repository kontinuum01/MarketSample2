package ru.gb.android.workshop2.presentation.list.start.product

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import ru.gb.android.workshop2.domain.product.ConsumeProductsUseCase
import ru.gb.android.workshop2.domain.promo.ConsumePromosUseCase

class ProductListPresenter(
    private val consumeProductsUseCase: ConsumeProductsUseCase,
    private val productVOFactory: ProductVOFactory,
    private val consumePromosUseCase: ConsumePromosUseCase,
) {
    private lateinit var coroutineScope : CoroutineScope

    private var _view: ProductListView? = null
    private val view: ProductListView
        get() = _view!!

    fun onViewAttached(view: ProductListView) {
        _view = view
        coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    }

    fun loadProduct() {
        combine(
            consumeProductsUseCase(),
            consumePromosUseCase(),
        ) { products, promos ->
            products.map { product -> productVOFactory.create(product, promos) }
        }
            .onStart {
                view.showProgress()
                view.hideProducts()
            }
            .onEach { productListVO ->
                view.hideProgress()
                view.hidePullToRefresh()
                view.showProducts(productListVO)
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
        loadProduct()
    }
}
