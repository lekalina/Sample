package com.lekalina.kotlin.sample.viewmodel

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide

abstract class BaseViewModel: ViewModel() {

    companion object {
        /**
         * Method for loading urls in XML via databinding
         */
        @BindingAdapter("imageUrl")
        @JvmStatic
        fun loadImage(imageView: ImageView, url: String) {
            if (url != "") {
                Glide.with(imageView.context)
                    .load(url)
                    .override(1000,1000) // scaling for faster loading
                    .centerCrop()
                    .into(imageView)
            }
        }
    }
}