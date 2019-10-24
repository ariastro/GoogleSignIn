package com.astronout.googlesignin.utils.binding

import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.astronout.googlesignin.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions

class BindingConverters {
    companion object {
        @JvmStatic
        @BindingAdapter("loadImage")
        fun loadImage(imageView: AppCompatImageView, url: Int) {
            Glide.with(imageView.context)
                .load(url)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                        .format(DecodeFormat.PREFER_ARGB_8888)
                )
                .into(imageView)
        }
    }
}