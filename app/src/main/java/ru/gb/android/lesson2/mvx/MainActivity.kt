package ru.gb.android.lesson2.mvx

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ru.gb.android.lesson2.marketsample.R
import ru.gb.android.lesson2.marketsample.databinding.ActivityMainBinding
import ru.gb.android.lesson2.mvx.features.product.presentation.mvvm.ProductFragment as MVVM
import ru.gb.android.lesson2.mvx.features.product.presentation.mvp1.ProductFragment as MVP1
import ru.gb.android.lesson2.mvx.features.product.presentation.mvp2.ProductFragment as MVP2
import ru.gb.android.lesson2.mvx.features.product.presentation.mvp3.ProductFragment as MVP3
import ru.gb.android.lesson2.mvx.features.product.presentation.mvp4.ProductFragment as MVP4
import ru.gb.android.lesson2.mvx.features.product.presentation.mvi.ProductFragment as MVI

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mvp1.setOnClickListener { show(MVP1::class.java) }
        binding.mvp2.setOnClickListener { show(MVP2::class.java) }
        binding.mvp3.setOnClickListener { show(MVP3::class.java) }
        binding.mvp4.setOnClickListener { show(MVP4::class.java) }
        binding.mvvm.setOnClickListener { show(MVVM::class.java) }
        binding.mvi.setOnClickListener { show(MVI::class.java) }
    }

    private fun show(fragmentClass: Class<out Fragment>) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragmentClass, null)
            .addToBackStack(fragmentClass.name)
            .commit()
    }
}