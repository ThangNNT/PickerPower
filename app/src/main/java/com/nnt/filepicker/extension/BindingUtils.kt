package com.nnt.filepicker.extension

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.RoundedCornersTransformation
import com.nnt.filepicker.imagepicker.model.Bucket


@BindingAdapter("loadImageBucket")
fun loadImageBucket(view: ImageView, bucket: Bucket?){
    bucket?.images?.getOrNull(0)?.let {
        view.load(it.uri){
            transformations(RoundedCornersTransformation(16f.toPxFromDp(view.context)))
        }
    }
}

@BindingAdapter("text")
fun loadImageBucket(view: TextView, int: Int){
   view.text = int.toString()
}