package com.fady.venuevoyage.presentation.utils.common

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindingAdapter {


    @JvmStatic
    @BindingAdapter("setNetworkImage")
    fun setNetworkImage(view: ImageView, image: String?) {
        if (image == null) return
        Glide.with(view.context).load(image).into(view)
    }


    @JvmStatic
    @BindingAdapter("visibleGone")
    fun showHide(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.GONE
    }
}