package com.nnt.filepicker.imagepicker.model

import android.net.Uri
import java.io.Serializable

data class ImageModel(val id: Long, val uri: Uri): Serializable
data class Bucket(val id: Int, val displayName: String, val images: ArrayList<ImageModel>): Serializable