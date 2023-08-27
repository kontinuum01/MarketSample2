package ru.gb.android.lesson2.mvx.features.product.presentation.mvi

import com.badoo.mvicore.element.Reducer
import ru.gb.android.lesson2.mvx.features.product.presentation.mvi.entities.Effect
import ru.gb.android.lesson2.mvx.features.product.presentation.mvi.entities.State

class ReducerImpl : Reducer<State, Effect> {

    override fun invoke(state: State, effect: Effect): State {
        return when (effect) {
            Effect.Loading -> state.copy(
                isLoading = true,
            )
            is Effect.ProductLoaded -> state.copy(
                isLoading = false,
                product = effect.product,
            )
            is Effect.Error -> state.copy(
                isLoading = false,
            )
        }
    }
}