package com.nnt.filepicker.imagepicker

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.nnt.filepicker.R
import com.nnt.filepicker.databinding.ActivityGalleryBinding
import com.nnt.filepicker.extension.kodeinViewModel
import com.nnt.filepicker.imagepicker.buckets.BucketFragment
import com.nnt.filepicker.imagepicker.model.Bucket
import com.nnt.filepicker.imagepicker.images.ImageFragment
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein

class GalleryActivity : AppCompatActivity(), KodeinAware {
    override val kodein by kodein()
    private val viewModel: GalleryViewModel by kodeinViewModel()

    lateinit var binding: ActivityGalleryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_gallery)
        setup()
        setupObserver()
    }

    private fun setup(){
        if(viewModel.isInit){
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, BucketFragment.newInstance(), BucketFragment.TAG)
                .commit()
            viewModel.isInit = false
        }
        viewModel.getImageBucketList(contentResolver)
    }

    private fun setupObserver() {
    }

    fun onBucketSelected(bucket: Bucket){
        viewModel.selectedImageIds.clear()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, ImageFragment.newInstance(bucket), BucketFragment.TAG)
            .addToBackStack(ImageFragment.TAG)
            .commit()
        viewModel.selectBucket(bucket)
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, GalleryActivity::class.java)
    }
}