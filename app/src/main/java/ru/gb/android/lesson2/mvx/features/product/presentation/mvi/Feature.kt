package ru.gb.android.lesson2.mvx.features.product.presentation.mvi

import com.badoo.mvicore.feature.ActorReducerFeature
import ru.gb.android.lesson2.mvx.common.promo.domain.ConsumePromosUseCase
import ru.gb.android.lesson2.mvx.features.product.domain.ConsumeFirstProductUseCase
import ru.gb.android.lesson2.mvx.features.product.presentation.mvi.entities.Effect
import ru.gb.android.lesson2.mvx.features.product.presentation.mvi.entities.News
import ru.gb.android.lesson2.mvx.features.product.presentation.mvi.entities.State
import ru.gb.android.lesson2.mvx.features.product.presentation.mvi.entities.Wish

class Feature(
    consumeFirstProductUseCase: ConsumeFirstProductUseCase,
    productStateFactory: ProductStateFactory,
    consumePromosUseCase: ConsumePromosUseCase,
) : ActorReducerFeature<Wish, Effect, State, News>(
    initialState = State(),
    actor = ActorImpl(
        consumeFirstProductUseCase = consumeFirstProductUseCase,
        productStateFactory = productStateFactory,
        consumePromosUseCase = consumePromosUseCase,
    ),
    reducer = ReducerImpl(),
    newsPublisher = NewsPublisherImpl()
)







































/*
class View: Consumer<ViewModel> {

    private val button: Button = ...

    // Specify the fields to observe and actions to execute
    private val watcher = modelWatcher<ViewModel> {
        watch(ViewModel::buttonText) {
            button.text = it
        }
        watch(ViewModel::buttonAction, diff = byRef()) {
            button.setOnClickListener { it() }
        }
    }

    override fun accept(model) {
        // Pass the model
        watcher.invoke(model)
    }
}*/
