package com.nnt.filepicker.imagepicker.datasource

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.ContentUris
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import com.nnt.filepicker.imagepicker.datasource.Contanst.BUCKET_ALL
import com.nnt.filepicker.imagepicker.model.Bucket
import com.nnt.filepicker.imagepicker.model.ImageModel
import java.util.ArrayList

interface ImageDataSource {
    suspend fun getImageBucket(contentResolver: ContentResolver, onlyMimeType: Set<String>, exceptMimeType: Set<String>): List<Bucket>
}

class ImageDataSourceImpl: ImageDataSource{
    @SuppressLint("Recycle")
    override suspend fun getImageBucket(contentResolver: ContentResolver, onlyMimeType: Set<String>, exceptMimeType: Set<String>): List<Bucket> {
        val uri = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
        } else MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.BUCKET_ID,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Images.Media.MIME_TYPE
        )
        val cursor = contentResolver.query(uri, projection, null, null, null)
        val buckets = HashMap<Int, Bucket>()
        buckets[BUCKET_ALL]= Bucket(BUCKET_ALL, "All", ArrayList<ImageModel>())
        Log.d("AAAAAAAAAAAA", "${cursor?.moveToFirst()}")

        if (cursor?.moveToFirst() == true) {
            val idColumn = cursor.getColumnIndex(MediaStore.Images.Media._ID)
            val bucketNameColumn = cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
            val bucketIdColumn = cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_ID)
            val mimeTypeColumn = cursor.getColumnIndex(MediaStore.Images.Media.MIME_TYPE)
            do {
                val id = cursor.getLong(idColumn)
                val imageUri = ContentUris.withAppendedId(uri, id)
                val bucketId = cursor.getInt(bucketIdColumn)
                val bucketName = cursor.getString(bucketNameColumn)
                val mimeType = cursor.getString(mimeTypeColumn)
                if((onlyMimeType.isNotEmpty()&& !onlyMimeType.contains(mimeType)) || (exceptMimeType.isNotEmpty() && exceptMimeType.contains(mimeType))){
                    continue
                }

                Log.d("AAAAAAAAAAAA", "$id, ${imageUri}, $mimeType, ${bucketId}, ${bucketName}")

                val imageModel = ImageModel(id, imageUri)
                buckets[BUCKET_ALL]?.images?.add(imageModel)
                if(buckets.containsKey(bucketId)){
                    buckets[bucketId]?.images?.add(imageModel)
                }
                else {
                    buckets[bucketId] = Bucket(bucketId, bucketName, arrayListOf(imageModel))
                }
            } while (cursor.moveToNext())
        }
        return buckets.values.toList()
    }

}