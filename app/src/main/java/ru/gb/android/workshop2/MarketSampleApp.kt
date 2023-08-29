package ru.gb.android.workshop2

import android.app.Application

class MarketSampleApp: Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: MarketSampleApp? = null
        fun getInstance(): MarketSampleApp = instance!!
    }
}
