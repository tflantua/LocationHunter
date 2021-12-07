package com.bijgepast.locationhunter.adapters

import android.widget.ImageView

import androidx.databinding.BindingAdapter
import com.bijgepast.locationhunter.R

@BindingAdapter("isUnlocked")
fun setSendState(v: ImageView, isUnlocked: Boolean) {
    val drawableRes: Int =
        if (isUnlocked) R.drawable.ic_baseline_lock_open_24 else R.drawable.ic_outline_lock_24
    v.setImageResource(drawableRes)
}
