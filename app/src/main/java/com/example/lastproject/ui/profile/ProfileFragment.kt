package com.example.lastproject.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import com.example.lastproject.Extensions.showAlert
import com.example.lastproject.Extensions.showSnackBar
import com.example.lastproject.R
import com.example.lastproject.data.state.GetProfileState
import com.example.lastproject.data.state.UpdateProfileState
import com.example.lastproject.databinding.FragmentProfileBinding
import com.example.lastproject.ui.dashboard.DashboardFragment.Companion.USER
import kotlinx.coroutines.launch


class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileFragmentViewModel by activityViewModels()
    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            binding.civProfilePhoto.load(uri)
            viewModel.updateProfilePhoto(uri)
        } else {
            //
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)

        arguments?.getInt(USER,-1)?.let {
            viewModel.getProfileInfo(it)
        }
        observeMessage()
        observeUpdateProfileState()
        observeGetProfileState()
        listeners()

    }

    private fun observeGetProfileState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.getProfileState.collect {
                    when (it) {
                        GetProfileState.Idle -> {}
                        GetProfileState.Loading -> {}
                        is GetProfileState.Success -> {
                            binding.etEmail.setText(it.user.email)
                            binding.etUsername.setText(it.user.username)
                            binding.etPassword.setText(it.user.password)
                        }

                        GetProfileState.Error -> {}
                    }
                }
            }
        }
    }

    private fun observeMessage() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.message.collect {
                    requireContext().showAlert(it)
                }
            }
        }
    }

    private fun observeUpdateProfileState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.updateProfileState.collect {
                    when (it) {
                        UpdateProfileState.Idle -> {}
                        UpdateProfileState.Loading -> {}
                        UpdateProfileState.AlreadyUser -> {}
                        UpdateProfileState.Success -> {
                            requireContext().showSnackBar(binding.etUsername,"Güncelleme işlemi başarılı")
                        }
                        UpdateProfileState.Error -> {}

                    }
                }
            }
        }
    }
    private fun listeners(){
        binding.btnUpdate.setOnClickListener {
            viewModel.updateProfile(
                binding.etUsername.text.toString().trim(),
                binding.etEmail.text.toString().trim(),
                binding.etPassword.text.toString().trim()
            )
        }
        binding.btnEdit.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
    }
}