package ru.gb.android.lesson2.mvx.features.product.presentation.mvp2

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext
import ru.gb.android.lesson2.mvx.common.promo.domain.ConsumePromosUseCase
import ru.gb.android.lesson2.mvx.features.product.domain.ConsumeFirstProductUseCase
import ru.gb.android.lesson2.mvx.features.product.presentation.ProductVOFactory

class ProductPresenter(
    private val consumeFirstProductUseCase: ConsumeFirstProductUseCase,
    private val productVOFactory: ProductVOFactory,
    private val consumePromosUseCase: ConsumePromosUseCase,
) {
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private var _view: ProductView? = null
    private val view: ProductView
        get() = _view!!

    fun onViewAttached(view: ProductView) {
        _view = view
    }

    fun loadProduct() {
        consumeFirstProductUseCase()
            .onStart {
                withContext(Dispatchers.Main) {
                    view.showProgress()
                    view.hideProduct()
                }
            }
            .flowOn(Dispatchers.IO)
            .flatMapLatest { product ->
                consumePromosUseCase().map { promos -> product to promos }
            }
            .map { (product, promos) ->
                productVOFactory.create(product, promos)
            }
            .flowOn(Dispatchers.IO)
            .onEach { productVO ->
                view.hideProgress()
                view.showProduct(productVO)
            }
            .catch { view.showError() }
            .launchIn(coroutineScope)
    }

    fun dispose() {
        coroutineScope.cancel()
    }

    fun addToCart() {
        // some domain logic
    }
}
