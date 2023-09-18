package com.example.lastproject.ui.photodetail

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import com.example.lastproject.Extensions.showSnackBar
import com.example.lastproject.Extensions.showToast
import com.example.lastproject.R
import com.example.lastproject.data.model.Photo
import com.example.lastproject.databinding.FragmentPhotoDetailBinding
import com.example.lastproject.ui.dashboard.DashboardFragment.Companion.PHOTOID
import com.example.lastproject.ui.dashboard.DashboardFragment.Companion.USER
import kotlinx.coroutines.flow.collect

import kotlinx.coroutines.launch


class PhotoDetailFragment : Fragment(R.layout.fragment_photo_detail) {

    private lateinit var binding: FragmentPhotoDetailBinding
    private val viewModel: PhotoDetailFragmentViewModel by activityViewModels()
    lateinit var photo: Photo
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPhotoDetailBinding.bind(view)

        arguments?.getParcelable<Photo>(PHOTOID)?.let {
            binding.ivPhoto.load(it.src.portrait)
            binding.tvPhotographer.text=it.photographer
            viewModel.checkFavourite(it)
            photo = it
        }

        observeIsFavourite()
        observeMessage()
        listeners()
    }

    private fun observeIsFavourite(){
        viewLifecycleOwner.lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.CREATED){
                viewModel.isFavourite.collect{
                    if(it)
                        binding.ivAddFavourite.setImageResource(R.drawable.baseline_star_24)
                    else
                        binding.ivAddFavourite.setImageResource(R.drawable.baseline_star_outline_24)
                }
            }
        }
    }
    private fun observeMessage(){
        viewLifecycleOwner.lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.CREATED){
                viewModel.message.collect{
                    if(it) requireContext().showSnackBar(binding.ivPhoto,"Favoriye Eklendi")
                    else requireContext().showSnackBar(binding.ivPhoto,"Favorilerden Çıkarıldı")
                }
            }
        }
    }
    private fun listeners() {
        binding.btnShare.setOnClickListener {
            try {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                val message ="${binding.wvPhotographer.loadUrl("www.google.com")}"
                shareIntent.putExtra(Intent.EXTRA_TEXT, message)
                startActivity(shareIntent)

            } catch (e: ActivityNotFoundException) {
                requireContext().showToast("application was not found in this device")
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=com.findwordgame.app")
                    )
                )
            }
        }

        binding.ivAddFavourite.setOnClickListener {
            arguments?.getInt(USER,-1)?.let {
                viewModel.insertOrDelete(photo,it)
            }

        }

        binding.tvPhotographer.setOnClickListener {
            binding.wvPhotographer.isVisible = true
            binding.wvPhotographer.loadUrl(photo.photographer_url)
            binding.wvPhotographer.isVisible=false
        }

    }
}
