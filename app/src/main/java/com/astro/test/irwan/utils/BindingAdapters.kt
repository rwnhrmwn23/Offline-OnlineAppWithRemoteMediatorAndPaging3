package com.astro.test.irwan.utils

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.astro.test.irwan.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("android:loadImage")
fun ImageView.loadImage(url: Any?) {
    try {
        val options = RequestOptions()
            .placeholder(getProgressDrawable(context))
            .error(R.mipmap.ic_launcher)
        Glide.with(context)
            .setDefaultRequestOptions(options)
            .load(url)
            .circleCrop()
            .into(this)
    } catch (e: Exception) {
        e.toString()
    }
}

fun getProgressDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 10F
        centerRadius = 50F
        start()
    }
}