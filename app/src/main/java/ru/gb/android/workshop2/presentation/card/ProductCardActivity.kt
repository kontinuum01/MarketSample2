package ru.gb.android.workshop2.presentation.card

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.gb.android.workshop2.marketsample.databinding.ActivityProductCardBinding

class ProductCardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductCardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProductCardBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}