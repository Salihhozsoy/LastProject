package com.example.lastproject.ui.longclick

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import com.example.lastproject.R
import com.example.lastproject.data.model.Photo
import com.example.lastproject.databinding.FragmentLongClickBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class LongClickFragment(private val photo:Photo) : BottomSheetDialogFragment() {

    private lateinit var binding:FragmentLongClickBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLongClickBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivPhoto.load(photo.src.portrait)
    }
}