package ru.gb.android.lesson2.mvx

import android.app.Application

class MarketSampleApp: Application() {
    override fun onCreate() {
        super.onCreate()
        Injector.applicationContext = this
    }
}
