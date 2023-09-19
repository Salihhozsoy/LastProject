package com.example.lastproject.ui.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
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
import com.example.lastproject.ui.adapter.PhotoAdapter
import com.example.lastproject.ui.login.LoginFragment.Companion.USERID
import com.example.lastproject.ui.longclick.LongClickFragment
import com.example.lastproject.ui.photodetail.PhotoDetailFragment
import com.example.lastproject.util.ConnectivityObserver
import kotlinx.coroutines.launch


class DashboardFragment : Fragment(R.layout.fragment_dashboard) {

    private lateinit var binding: FragmentDashboardBinding
    private val viewModel: DashboardFragmentViewModel by activityViewModels()
    private var isNetCheck:Boolean =true

    companion object {
        const val PHOTOID = "photoId"
        const val USER = "userId"

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDashboardBinding.bind(view)

        observePhotoListState()
        observeGetCategoryState()
        observeInternetConnection()
        listeners()
        viewModel.getAllCategory()
    }

    private fun observeInternetConnection() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.connectivityStatus.collect {
                    when (it) {
                        ConnectivityObserver.NetworkStatus.Available -> {
                            isNetCheck = true
                            binding.rvPhotos.isVisible =true
                            binding.btnAddIcon.isVisible=true
                            binding.btnFavouriteList.isVisible=true
                            binding.spCategories.isVisible=true
                        }

                        else -> {
                            isNetCheck = false
                            binding.llTakePicture.isVisible = true
                            binding.llAddCategory.isVisible = false
                            binding.rvPhotos.isVisible =false
                            binding.btnAddIcon.isVisible=false
                            binding.rvPhotos.isVisible=false
                            binding.btnFavouriteList.isVisible=false
                            binding.spCategories.isVisible=false
                        }
                    }
                }
            }
        }
    }

    private fun observeGetCategoryState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.getCategoryState.collect {
                    when (it) {
                        GetCategoryState.Idle -> {}
                        GetCategoryState.Empty -> {
                            binding.llAddCategory.isVisible = true
                            binding.llTakePicture.isVisible = false
                            binding.rvPhotos.isVisible = false
                        }

                        is GetCategoryState.Success -> {
                            if(isNetCheck){
                                binding.llTakePicture.isVisible = false

                            }
                            binding.llAddCategory.isVisible = false
                            binding.rvPhotos.isVisible = true
                            binding.spCategories.adapter = ArrayAdapter(
                                requireContext(),
                                android.R.layout.simple_list_item_1,
                                it.categories.map { it.categoryName })
                            binding.btnProfilePage.text="Profile"
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
                            binding.rvPhotos.adapter = PhotoAdapter(
                                requireContext(),
                                it.photos,
                                this@DashboardFragment::onClick,
                                this@DashboardFragment::onLongClick
                            )
                        }

                        PhotoListState.Error -> {}
                    }
                }
            }
        }
    }

    private fun listeners() {
        val userId = arguments?.getInt(USERID, -1)

        binding.btnAddIcon.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_addCategoryFragment)
        }
        binding.btnAddCategory.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_addCategoryFragment)
        }
        binding.btnTakePicture.setOnClickListener {

        }
        binding.btnProfilePage.setOnClickListener {
            if (userId != null)
                findNavController().navigate(
                    R.id.action_dashboardFragment_to_profileFragment,
                    bundleOf(USER to userId)
                )
        }
        binding.spCategories.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.categorySelected(position)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
        binding.btnFavouriteList.setOnClickListener {
            if(userId !=null)
                findNavController().navigate(R.id.action_dashboardFragment_to_favouritesFragment, bundleOf(USER to userId))
        }
    }

    private fun onClick(photo: Photo) {
        val userId = arguments?.getInt(USERID, -1)
        val bundle = bundleOf()
        bundle.putParcelable(PHOTOID, photo)
        if (userId != null) bundle.putInt(USER, userId)
        val photoDetailFragment2 = PhotoDetailFragment()
        photoDetailFragment2.arguments = bundle
        findNavController().navigate(R.id.action_dashboardFragment_to_photoDetailFragment, bundle)
    }
    private fun onLongClick(photo:Photo){
        val photoDetailFragment = LongClickFragment(photo)
        photoDetailFragment.show(requireActivity().supportFragmentManager, null)
    }

}