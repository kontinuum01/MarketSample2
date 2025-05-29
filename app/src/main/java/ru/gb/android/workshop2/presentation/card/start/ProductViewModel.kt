package ru.gb.android.workshop2.presentation.card.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import ru.gb.android.workshop2.domain.product.ConsumeFirstProductUseCase
import ru.gb.android.workshop2.domain.promo.ConsumePromosUseCase
import ru.gb.android.workshop2.presentation.card.finish.ScreenState

class ProductViewModel(
    private val consumeFirstProductUseCase: ConsumeFirstProductUseCase,
    private val productStateFactory: ProductStateFactory,
    private val consumePromosUseCase: ConsumePromosUseCase,
) : ViewModel() {
    private val _productState = MutableStateFlow(ScreenState())
    val productState: StateFlow<ScreenState> = _productState.asStateFlow()

    fun loadProduct() {
        combine(
            consumeFirstProductUseCase(),
            consumePromosUseCase(),
        ) { product, promos -> productStateFactory.create(product, promos) }
            .onEach { productSate ->

//                view.hideProgress()
//                view.showName(productVO.name)
//                view.showImage(productVO.image)
//                if (productVO.hasDiscount) {
//                    view.showPromo(productVO.discount)
//                } else {
//                    view.hidePromo()
//                }
//                view.showPrice(productVO.price)
//                view.showAddToCart()
            }
            .launchIn(viewModelScope)
    }

    fun dispose() {
        viewModelScope.cancel()
    }
}

