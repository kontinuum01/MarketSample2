package ru.gb.android.workshop2.presentation.list.product.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.gb.android.workshop2.marketsample.databinding.ItemProductBinding
import ru.gb.android.workshop2.presentation.list.product.ProductListState

class ProductsAdapter : ListAdapter<ProductListState, ProductHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        return ProductHolder(
            ItemProductBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        val entity = getItem(position)
        entity?.let {
            holder.bind(entity)
        }
    }


    private class DiffCallback : DiffUtil.ItemCallback<ProductListState>() {

        override fun areItemsTheSame(oldItem: ProductListState, newItem: ProductListState): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductListState, newItem: ProductListState): Boolean {
            return oldItem == newItem
        }
    }
}
