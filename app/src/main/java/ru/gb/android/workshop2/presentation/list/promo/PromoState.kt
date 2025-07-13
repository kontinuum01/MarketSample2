package ru.gb.android.workshop2.presentation.list.promo

import android.util.Log

data class PromoState (
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val image: String = "",
) {
     init {
         Log.d( "PromoInit", "id = $id, name = $name, description = $description, image = $image")
     }
 }
