package com.example.lastproject.ui.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.lastproject.R
import com.example.lastproject.data.model.Photo
import com.example.lastproject.data.state.GetCategoryState
import com.example.lastproject.data.state.PhotoListState
import com.example.lastproject.databinding.FragmentDashboardBinding
import com.example.lastproject.ui.longclick.LongClickFragment
import kotlinx.coroutines.launch


class DashboardFragment : Fragment(R.layout.fragment_dashboard) {

    private lateinit var binding: FragmentDashboardBinding
    private val viewModel:DashboardFragmentViewModel by activityViewModels()
    companion object{
        const val PHOTOID="photoid"
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentDashboardBinding.bind(view)

        observePhotoListState()
        observeGetCategoryState()
        listeners()
        viewModel.getAllCategory()
    }

    private fun observeGetCategoryState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
               viewModel.getCategoryState.collect {
                    when (it) {
                        GetCategoryState.Idle -> {}
                        GetCategoryState.Empty -> {
                            binding.llAddCategory.isVisible=true
                            binding.llTakePicture.isVisible=false
                            binding.rvPhotos.isVisible=false
                        }
                        is GetCategoryState.Success -> {
                            binding.llAddCategory.isVisible = false
                            binding.llTakePicture.isVisible = false
                            binding.rvPhotos.isVisible = true
                            binding.spCategories.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1,it.categories)

                        }

                        is GetCategoryState.Error -> {}
                    }
                }
            }
        }
    }
    private fun observePhotoListState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.photoListState.collect {
                    when (it) {
                        PhotoListState.Idle -> {}
                        PhotoListState.Loading -> {}
                        PhotoListState.Empty -> {}
                        is PhotoListState.Result -> {
                            binding.rvPhotos.adapter = PhotoAdapter(requireContext(),it.photos,this@DashboardFragment::onClick)
                        }
                        PhotoListState.Error -> {}
                    }
                }
            }
        }
    }
    private fun listeners(){
        binding.btnAddCategory.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_addCategoryFragment)
        }
        binding.btnTakePicture.setOnClickListener {

        }
        binding.btnProfilePage.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_profileFragment)
        }
        binding.spCategories.onItemSelectedListener =object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            viewModel.categorySelected(position)
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
    }
    private fun onClick(photo: Photo) {
       // val photoDetailFragment = LongClickFragment(photo)
      //  photoDetailFragment.show(supportFragmentManager, null)

        findNavController().navigate(R.id.action_dashboardFragment_to_photoDetailFragment,
            bundleOf(PHOTOID to photo.id)

        )
    }

}