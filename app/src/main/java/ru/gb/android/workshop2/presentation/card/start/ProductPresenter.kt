package ru.gb.android.workshop2.presentation.card.start

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import ru.gb.android.workshop2.domain.product.ConsumeFirstProductUseCase
import ru.gb.android.workshop2.domain.promo.ConsumePromosUseCase

class ProductPresenter(
    private val consumeFirstProductUseCase: ConsumeFirstProductUseCase,
    private val productVOFactory: ProductVOFactory,
    private val consumePromosUseCase: ConsumePromosUseCase,
) {
    private lateinit var coroutineScope : CoroutineScope

    private var _view: ProductView? = null
    private val view: ProductView
        get() = _view!!

    fun onViewAttached(view: ProductView) {
        _view = view
        coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    }

    fun loadProduct() {
        combine(
            consumeFirstProductUseCase(),
            consumePromosUseCase(),
        ) { product, promos -> productVOFactory.create(product, promos) }
            .onStart {
                view.showProgress()
                view.hideName()
                view.hideImage()
                view.hidePromo()
                view.hidePrice()
                view.hideAddToCart()
            }
            .onEach { productVO ->
                view.hideProgress()
                view.showName(productVO.name)
                view.showImage(productVO.image)
                if (productVO.hasDiscount) {
                    view.showPromo(productVO.discount)
                } else {
                    view.hidePromo()
                }
                view.showPrice(productVO.price)
                view.showAddToCart()
            }
            .catch {
                view.showError()
            }
            .launchIn(coroutineScope)
    }

    fun dispose() {
        coroutineScope.cancel()
    }
}
