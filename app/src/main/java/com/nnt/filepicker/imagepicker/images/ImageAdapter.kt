package com.nnt.filepicker.imagepicker.images

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.nnt.filepicker.R
import com.nnt.filepicker.databinding.ItemImageBinding
import com.nnt.filepicker.imagepicker.model.ImageModel

class ImageAdapter(
    private var images: List<ImageModel>,
    private val initSelectedImage: Set<Long>,
    private val maxCount: Int,
    private val onImageClicked: (image: ImageModel) -> Unit,
    private val onLimitSelectedImagesReach: () -> Unit
) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {
    private var currentSelectedCount = initSelectedImage.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = parent.context.getSystemService(LayoutInflater::class.java)
        val binding: ItemImageBinding =
            DataBindingUtil.inflate(inflater, R.layout.item_image, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = holder.binding
        val image = images[position]
        binding.image = image
        if(initSelectedImage.contains(image.id)){
            image.isSelected = true
        }
        binding.ivSelected.isVisible = image.isSelected
        binding.root.setOnClickListener {
            if(currentSelectedCount==maxCount){
                if(!image.isSelected){
                    onLimitSelectedImagesReach.invoke()
                }
                else {
                    onChangeImageSelectStatus(binding, image)
                }
            }
            else {
                onChangeImageSelectStatus(binding, image)
            }
        }
    }

    private fun onChangeImageSelectStatus(binding: ItemImageBinding, model: ImageModel){
        model.isSelected = !model.isSelected
        if(model.isSelected){
            currentSelectedCount++
        }
        else {
            currentSelectedCount--
        }
        onImageClicked.invoke(model)
        binding.ivSelected.isVisible = model.isSelected
    }

    override fun getItemCount(): Int {
        return images.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(data: List<ImageModel>) {
        images = data
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root)
}