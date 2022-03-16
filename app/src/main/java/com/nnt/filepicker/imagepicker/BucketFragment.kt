package com.nnt.filepicker.imagepicker

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.nnt.filepicker.R
import com.nnt.filepicker.databinding.FragmentBucketBinding
import com.nnt.filepicker.extension.kodeinViewModelFromActivity
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein

class BucketFragment: Fragment(), KodeinAware {
    override val kodein by kodein()
    private val parentViewModel: GalleryViewModel by kodeinViewModelFromActivity()
    lateinit var binding: FragmentBucketBinding
    private var adapter = BucketAdapter(emptyList())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bucket, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
        setupObserver()
    }

    private fun setup(){
        binding.rvBucket.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = this@BucketFragment.adapter
        }
    }

    private fun setupObserver(){
        parentViewModel.imageBucket.observe(viewLifecycleOwner){
            Log.d("============>", it.toString())
            adapter.updateData(it)
        }
    }
    companion object {
        val TAG = BucketFragment::class.java.simpleName
        fun newInstance() = BucketFragment()
    }
}