package com.bijgepast.locationhunter.adapters

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView

import androidx.databinding.BindingAdapter
import com.bijgepast.locationhunter.R
import com.google.android.material.slider.Slider

@BindingAdapter("isUnlocked")
fun setSendState(v: ImageView, isUnlocked: Boolean) {
    val drawableRes: Int =
        if (isUnlocked) R.drawable.ic_baseline_lock_open_24 else R.drawable.ic_outline_lock_24
    v.setImageResource(drawableRes)
}

@BindingAdapter("goneUnless")
fun goneUnless(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter("startAnimation")
fun animation(v: ImageView, boolean: Boolean){
    Log.d(null, "start Animation was called")
    if(v.drawable is AnimationDrawable){
        val a = v.drawable as AnimationDrawable
        a.start()
    }
}

