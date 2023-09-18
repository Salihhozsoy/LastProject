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
import com.example.lastproject.Extensions.showToast
import com.example.lastproject.R
import com.example.lastproject.databinding.FragmentPhotoDetailBinding
import com.example.lastproject.ui.dashboard.DashboardFragment.Companion.PHOTOID
import kotlinx.coroutines.launch


class PhotoDetailFragment : Fragment(R.layout.fragment_photo_detail) {

    private lateinit var binding: FragmentPhotoDetailBinding
    private val viewModel: PhotoDetailFragmentViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPhotoDetailBinding.bind(view)

        observePhotoDetailState()
        listeners()

        arguments?.getInt(PHOTOID, -1)?.let { photoId ->
            viewModel.getPhotoById(photoId)
        }

    }

    private fun observePhotoDetailState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.photoDetailState.collect {
                    it?.let {
                        binding.ivPhoto.load(it.src.portrait)
                        binding.wvPhotographer.isVisible = true
                        binding.wvPhotographer.loadUrl(it.photographer_url)
                    }
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
    }
}