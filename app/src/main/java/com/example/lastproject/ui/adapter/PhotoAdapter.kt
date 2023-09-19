package com.example.lastproject.ui.adapter

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
    val onClick: (photo: Photo) -> Unit,
    val onLongClick: (photo: Photo) -> Unit
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
            onLongClick(photo)
            return@setOnLongClickListener true
        }
        holder.itemView.setOnClickListener{
            onClick(photo)
        }

    }


    override fun getItemCount(): Int {
        return photos.size
    }
}