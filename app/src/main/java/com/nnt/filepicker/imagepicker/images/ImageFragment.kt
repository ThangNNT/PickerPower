package com.nnt.filepicker.imagepicker.images

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.nnt.filepicker.R
import com.nnt.filepicker.databinding.FragmentPhotoBinding
import com.nnt.filepicker.extension.kodeinViewModelFromActivity
import com.nnt.filepicker.imagepicker.GalleryViewModel
import com.nnt.filepicker.imagepicker.model.Bucket
import com.nnt.filepicker.imagepicker.model.ImageModel
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein

class ImageFragment: Fragment(), KodeinAware {
    override val kodein by kodein()
    private val parentViewModel: GalleryViewModel by kodeinViewModelFromActivity()
    lateinit var binding: FragmentPhotoBinding

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
        setup()
        setupObserver()
    }

    private fun setup(){

    }

    private fun setupObserver() {
        parentViewModel.selectedBucket.observe(viewLifecycleOwner) {
            binding.rvImages.apply {
                layoutManager = GridLayoutManager(requireContext(), SPAN_COUNT)
                adapter = ImageAdapter(
                    it.images,
                    parentViewModel.selectedImageIds,
                    ::onImageClicked
                )
            }
        }
    }

    private fun onImageClicked(image: ImageModel){
        parentViewModel.onImageClick(image)
    }

    companion object {
        private const val SPAN_COUNT = 3
        val TAG: String = ImageFragment::class.java.simpleName
        private const val BUCKET = "BUCKET"

        fun newInstance(bucket: Bucket) = ImageFragment().apply {
            arguments = Bundle().apply {
                putSerializable(BUCKET, bucket)
            }
        }
    }
}