package com.nnt.filepicker.imagepicker.buckets

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.nnt.filepicker.R
import com.nnt.filepicker.databinding.ItemBucketBinding
import com.nnt.filepicker.imagepicker.model.Bucket

class BucketAdapter(private var buckets: List<Bucket>, private val onBucketSelected: (bucket: Bucket) -> Unit): RecyclerView.Adapter<BucketAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = parent.context.getSystemService(LayoutInflater::class.java)
        val binding: ItemBucketBinding = DataBindingUtil.inflate(inflater, R.layout.item_bucket, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = holder.binding
        binding.bucket = buckets[position]
        binding.root.setOnClickListener { onBucketSelected.invoke(buckets[position]) }
    }

    override fun getItemCount(): Int {
        return buckets.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(data: List<Bucket>){
        buckets = data
        notifyDataSetChanged()
    }
    class ViewHolder(val binding: ItemBucketBinding): RecyclerView.ViewHolder(binding.root)
}