package com.nnt.filepicker.imagepicker

import android.content.ContentResolver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nnt.filepicker.imagepicker.datasource.ImageDataSource
import com.nnt.filepicker.imagepicker.model.Bucket
import com.nnt.filepicker.imagepicker.model.ImageModel
import kotlinx.coroutines.launch

class GalleryViewModel(private val imageSource: ImageDataSource): ViewModel() {
    private val _imageBuckets = MutableLiveData<List<Bucket>>()
    val imageBucket: LiveData<List<Bucket>> = _imageBuckets

    /**
     * if boolean value is true, you should observe it
     */
    private val _selectedBucket = MutableLiveData<Bucket>()
    val selectedBucket: LiveData<Bucket> = _selectedBucket

    //contain selected image id
    val selectedImageIds = LinkedHashSet<Long>()

    var isInit = true

    fun getImageBucketList(contentResolver: ContentResolver) = viewModelScope.launch {
        _imageBuckets.postValue(imageSource.getImageBucket(contentResolver, setOf("image/jpeg", "image/heic", "image/heif"), setOf("image/heic")))
    }

    fun selectBucket(bucket: Bucket){
        _selectedBucket.value = bucket
    }

    fun onImageClick(image: ImageModel){
        if (image.isSelected){
            selectedImageIds.add(image.id)
        }
        else {
            selectedImageIds.remove(image.id)
        }
    }
}