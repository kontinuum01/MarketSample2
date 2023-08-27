package ru.gb.android.lesson2.mvx.features.product.presentation.mvi

import com.badoo.mvicore.element.NewsPublisher
import ru.gb.android.lesson2.mvx.features.product.presentation.mvi.entities.Effect
import ru.gb.android.lesson2.mvx.features.product.presentation.mvi.entities.News
import ru.gb.android.lesson2.mvx.features.product.presentation.mvi.entities.State
import ru.gb.android.lesson2.mvx.features.product.presentation.mvi.entities.Wish

class NewsPublisherImpl : NewsPublisher<Wish, Effect, State, News> {

    override fun invoke(
        action: Wish,
        effect: Effect,
        state: State
    ): News? {
        return when (effect) {
            is Effect.Error -> News.ErrorExecutingRequest(effect.throwable)
            else -> null
        }
    }
}