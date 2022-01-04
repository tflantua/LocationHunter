package com.bijgepast.locationhunter.adapters

import android.app.Activity
import android.content.Intent
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bijgepast.locationhunter.R
import com.tomtom.online.sdk.search.location.Address


fun startActivity(activity: Activity, url: String, flags: Int) {
    activity.startActivity(Intent.parseUri(url, flags))
}

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

@BindingAdapter(value = ["currentRotation", "newRotation"], requireAll = true)
fun rotateAnimation(v: ImageView, currentDegree: Float, degree: Float) {
    val ra = RotateAnimation(
        -currentDegree,
        degree,
        Animation.RELATIVE_TO_SELF, 0.5f,
        Animation.RELATIVE_TO_SELF, 0.5f
    )

    // how long the animation will take place

    // how long the animation will take place
    ra.duration = 500

    // set the animation after the end of the reservation status

    // set the animation after the end of the reservation status
    ra.fillAfter = true

    // Start the animation

    // Start the animation
    v.startAnimation(ra)
}


@BindingAdapter("startAnimation")
fun animation(v: ImageView, animation: RotateAnimation) {
    v.startAnimation(animation)
}

@BindingAdapter("adress")
fun TextView.text(address: Address) {
    text = address.freeFormAddress
}

@BindingAdapter("distance")
fun TextView.text(distance: Double) {
    text = String.format("%.1f km", distance/1000)
}

