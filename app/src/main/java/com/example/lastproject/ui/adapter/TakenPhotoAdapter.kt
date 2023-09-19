package com.example.lastproject.ui.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.room.TypeConverter
import com.example.lastproject.data.locale.entity.TakenPhotoEntity
import com.example.lastproject.databinding.PhotoListItemBinding

class TakenPhotoAdapter(
    private val context: Context,
    private var takenPhotos: List<TakenPhotoEntity>,
) : RecyclerView.Adapter<TakenPhotoAdapter.CustomViewHolder>() {

    class CustomViewHolder(binding: PhotoListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val ivPhoto = binding.ivPhoto
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val binding = PhotoListItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return CustomViewHolder(binding)
    }
    @TypeConverter
    fun byteArrayToBitmap(byteArray: ByteArray?): Bitmap? {
        if (byteArray == null) {
            return null
        }
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val takenPhotos = takenPhotos[position]
        val asda =byteArrayToBitmap(takenPhotos.image)
        holder.ivPhoto.setImageBitmap(asda)
    }

    override fun getItemCount(): Int {
        return takenPhotos.size
    }
}