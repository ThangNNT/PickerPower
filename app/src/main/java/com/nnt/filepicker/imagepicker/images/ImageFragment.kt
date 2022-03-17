package com.nnt.filepicker.imagepicker.images

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
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
    private val maxCount by lazy { arguments?.getInt(MAX_COUNT, 1)?:1 }
    private val minCount by lazy { arguments?.getInt(MIN_COUNT, 1)?:1 }
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
                    maxCount,
                    ::onImageClicked,
                    ::onLimitSelectedImagesReach
                )
            }
        }
    }

    private fun onLimitSelectedImagesReach(){
        Snackbar.make(binding.root, "Vuot qua so anh cho phep", Snackbar.LENGTH_LONG).show()
    }

    private fun onImageClicked(image: ImageModel){
        parentViewModel.onImageClick(image)
    }

    companion object {
        private const val SPAN_COUNT = 3
        val TAG: String = ImageFragment::class.java.simpleName
        private const val BUCKET = "BUCKET"

        private const val MAX_COUNT = "MAX_COUNT"
        private const val MIN_COUNT = "MIN_COUNT"

        fun newInstance(bucket: Bucket, minCount: Int, maxCount: Int) = ImageFragment().apply {
            arguments = Bundle().apply {
                putSerializable(BUCKET, bucket)
                putInt(MIN_COUNT, minCount)
                putInt(MAX_COUNT, maxCount)
            }
        }
    }
}