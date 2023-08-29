package ru.gb.android.workshop2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.gb.android.workshop2.presentation.card.ProductCardActivity
import ru.gb.android.workshop2.presentation.list.ProductListActivity
import ru.gb.android.workshop2.marketsample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.productCard.setOnClickListener {
            Intent(this, ProductCardActivity::class.java).apply {
                startActivity(this)
            }
        }

        binding.productList.setOnClickListener {
            Intent(this, ProductListActivity::class.java).apply {
                startActivity(this)
            }
        }
    }
}