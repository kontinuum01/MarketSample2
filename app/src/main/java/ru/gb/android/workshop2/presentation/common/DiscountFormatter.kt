package ru.gb.android.workshop2.presentation.common

class DiscountFormatter {
    fun format(discount: Int): String {
        return String.format("%d %%", discount)
    }
}