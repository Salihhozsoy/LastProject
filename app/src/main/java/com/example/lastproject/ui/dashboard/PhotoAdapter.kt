package com.example.lastproject.ui.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.lastproject.R
import com.example.lastproject.data.model.Photo
import com.example.lastproject.databinding.PhotoListItemBinding

class PhotoAdapter(
    private val context: Context,
    private var photos: List<Photo>,
    val onClick: (photo: Photo) -> Unit
) : RecyclerView.Adapter<PhotoAdapter.CustomViewHolder>() {

    class CustomViewHolder(binding: PhotoListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val ivPhoto = binding.ivPhoto
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val binding = PhotoListItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return CustomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val photo = photos[position]
        holder.ivPhoto.load(photo.src.portrait)

        holder.itemView.setOnLongClickListener {
            val animation = AnimationUtils.loadAnimation(context, R.anim.animation)
            holder.ivPhoto.animation = animation
            animation.start()
            onClick(photo)
            return@setOnLongClickListener true
        }
    }


    override fun getItemCount(): Int {
        return photos.size
    }
}