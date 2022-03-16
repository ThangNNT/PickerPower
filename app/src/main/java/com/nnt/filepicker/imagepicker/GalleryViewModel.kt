package com.nnt.filepicker.imagepicker

import android.content.ContentResolver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nnt.filepicker.imagepicker.datasource.ImageDataSource
import com.nnt.filepicker.imagepicker.model.Bucket
import kotlinx.coroutines.launch

class GalleryViewModel(private val imageSource: ImageDataSource): ViewModel() {
    private val _imageBuckets = MutableLiveData<List<Bucket>>()
    val imageBucket: LiveData<List<Bucket>> = _imageBuckets

    fun getImageBucketList(contentResolver: ContentResolver) = viewModelScope.launch {
        _imageBuckets.postValue(imageSource.getImageBucket(contentResolver, setOf("image/jpeg", "image/heic", "image/heif"), setOf("image/heic")))
    }
}