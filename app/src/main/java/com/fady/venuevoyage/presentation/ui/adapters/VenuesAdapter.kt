package com.fady.venuevoyage.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.fady.venuevoyage.R
import com.fady.venuevoyage.data.models.Venue
import com.fady.venuevoyage.databinding.ItemVenueBinding
import com.fady.venuevoyage.presentation.utils.base.DataBoundListAdapter

class VenuesAdapter : DataBoundListAdapter<Venue, ItemVenueBinding>(diffCallback = object :
    DiffUtil.ItemCallback<Venue>() {
    override fun areContentsTheSame(
        oldItem: Venue, newItem: Venue
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areItemsTheSame(
        oldItem: Venue, newItem: Venue
    ): Boolean {
        return oldItem.id == newItem.id
    }
}) {


    override fun createBinding(parent: ViewGroup): ItemVenueBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_venue, parent, false
        )
    }


    override fun bind(
        binding: ItemVenueBinding, item: Venue, position: Int, adapterPosition: Int
    ) {
        binding.apply {
            venueNameTv.text = item.name
            venueAddressTv.text = item.location?.getFormattedAddress()
            item.categories?.firstOrNull()?.let {
                categoryName.text = it.name ?: ""
                categoryImage = it.icon?.getIconUrl()

            }
        }

    }


}