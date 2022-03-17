package com.nnt.filepicker.extension

import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.RoundedCornersTransformation
import com.bumptech.glide.Glide
import com.nnt.filepicker.R
import com.nnt.filepicker.imagepicker.model.Bucket


@BindingAdapter("loadImageBucket")
fun loadImageBucket(view: ImageView, bucket: Bucket?){
    bucket?.images?.getOrNull(0)?.let {
        Glide.with(view)
            .load(it.uri)
            .into(view)
    }
}

@BindingAdapter("loadImage")
fun loadImage(view: ImageView, uri: Uri?){
    Glide.with(view)
        .load(uri)
        .into(view)
}

@BindingAdapter("text")
fun loadImageBucket(view: TextView, int: Int){
   view.text = int.toString()
}

@BindingAdapter("showOrHide")
fun showOrHide(view: View, shouldShow: Boolean){
    view.isVisible = shouldShow
}