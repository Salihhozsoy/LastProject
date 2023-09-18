package com.example.lastproject.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.lastproject.R
import com.example.lastproject.data.locale.FavouriteEntity
import com.example.lastproject.data.model.Photo
import com.example.lastproject.databinding.FavouriteListItemBinding
import com.example.lastproject.databinding.PhotoListItemBinding

class FavouriteAdapter(
    private val context: Context,
    private var favouritePhotos: MutableList<FavouriteEntity>,
    val onRemove: (position:Int) -> Unit
) : RecyclerView.Adapter<FavouriteAdapter.CustomViewHolder>() {

    class CustomViewHolder(binding: FavouriteListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val ivFavouritePhoto = binding.ivFavouritePhoto
        val civStar=binding.civStar
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val binding = FavouriteListItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return CustomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val favouritePhotos = favouritePhotos[position]
        holder.ivFavouritePhoto.load(favouritePhotos.favouriteImageUrl)
        holder.civStar.setOnClickListener {
            onRemove(position)
        }

    }

    override fun getItemCount(): Int {
        return favouritePhotos.size
    }
}