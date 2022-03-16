package com.nnt.filepicker.imagepicker

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.nnt.filepicker.R
import com.nnt.filepicker.databinding.FragmentBucketBinding
import com.nnt.filepicker.databinding.FragmentPhotoBinding
import com.nnt.filepicker.extension.kodeinViewModelFromActivity
import com.nnt.filepicker.imagepicker.model.Bucket
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.android.x.kodein

class PhotoFragment: Fragment(), KodeinAware {
    override val kodein by kodein()

    lateinit var binding: FragmentPhotoBinding
    private val parentViewModel: GalleryViewModel by kodeinViewModelFromActivity()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_photo, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        private const val BUCKET = "BUCKET"
        fun newInstance(context: Context, bucket: Bucket) = PhotoFragment().apply {
            arguments = Bundle().apply {
                putSerializable(BUCKET, bucket)
            }
        }
    }
}