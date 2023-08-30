package ru.gb.android.workshop2.presentation.list.promo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.gb.android.workshop2.marketsample.databinding.ItemPromoBinding
import ru.gb.android.workshop2.presentation.list.promo.PromoVO

class PromoAdapter : ListAdapter<PromoVO, PromoHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PromoHolder {
        return PromoHolder(
            ItemPromoBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: PromoHolder, position: Int) {
        val entity = getItem(position)
        entity?.let {
            holder.bind(entity)
        }
    }
}

private class DiffCallback : DiffUtil.ItemCallback<PromoVO>() {

    override fun areItemsTheSame(oldItem: PromoVO, newItem: PromoVO): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PromoVO, newItem: PromoVO): Boolean {
        return oldItem == newItem
    }
}
