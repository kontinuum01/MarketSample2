package ru.gb.android.lesson2.mvx.features.product.presentation.mvi

import com.badoo.mvicore.element.Actor
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.rx2.rxSingle
import ru.gb.android.lesson2.mvx.common.promo.domain.ConsumePromosUseCase
import ru.gb.android.lesson2.mvx.features.product.domain.ConsumeFirstProductUseCase
import ru.gb.android.lesson2.mvx.features.product.presentation.mvi.entities.Effect
import ru.gb.android.lesson2.mvx.features.product.presentation.mvi.entities.ProductState
import ru.gb.android.lesson2.mvx.features.product.presentation.mvi.entities.State
import ru.gb.android.lesson2.mvx.features.product.presentation.mvi.entities.Wish

class ActorImpl(
    private val consumeFirstProductUseCase: ConsumeFirstProductUseCase,
    private val productStateFactory: ProductStateFactory,
    private val consumePromosUseCase: ConsumePromosUseCase,
) : Actor<State, Wish, Effect> {

    override fun invoke(state: State, wish: Wish): Observable<out Effect> {
        return when (wish) {
            is Wish.LoadProduct -> loadCharacters()
                .map { Effect.ProductLoaded(it) as Effect }
                .startWith((Effect.Loading))
                .onErrorReturn { Effect.Error(it) }

            is Wish.AddToCart -> {
                // TODO add to cart
                Observable.just(Effect.ProductLoaded(ProductState()))
            }
        }
    }

    private fun loadCharacters(): Observable<ProductState> {
        return rxSingle {
            consumeFirstProductUseCase()
                .flatMapLatest { product ->
                    consumePromosUseCase().map { promos -> product to promos }
                }
                .map { (product, promos) ->
                    productStateFactory.create(product, promos)
                }
                .first()
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .toObservable()
    }
}
