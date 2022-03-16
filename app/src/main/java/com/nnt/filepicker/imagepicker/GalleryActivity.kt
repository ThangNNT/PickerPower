package com.nnt.filepicker.imagepicker

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.nnt.filepicker.R
import com.nnt.filepicker.databinding.ActivityGalleryBinding
import com.nnt.filepicker.extension.kodeinViewModel
import com.nnt.filepicker.imagepicker.datasource.ImageDataSourceImpl
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
        supportFragmentManager.beginTransaction().add(R.id.fragment_container, BucketFragment.newInstance(), BucketFragment.TAG).commit()
    }

    private fun setupObserver(){
        viewModel.getImageBucketList(contentResolver)
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, GalleryActivity::class.java)
    }
}