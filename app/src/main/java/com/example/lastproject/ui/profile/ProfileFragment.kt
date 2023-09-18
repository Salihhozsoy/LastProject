package com.example.lastproject.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.lastproject.R
import com.example.lastproject.data.state.GetProfileState
import com.example.lastproject.data.state.UpdateProfileState
import com.example.lastproject.databinding.FragmentProfileBinding
import kotlinx.coroutines.launch


class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel:ProfileFragmentViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentProfileBinding.bind(view)


        observeUpdateProfileState()
        observeGetProfileState()
        viewModel.getPost(1)

        binding.btnUpdate.setOnClickListener {
            viewModel.updateProfile(
                binding.etEmailOrUsername.text.toString().trim(),
                binding.etPassword.text.toString().trim()
            )
        }
    }

    private fun observeGetProfileState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.getProfileState.collect {
                    when (it) {
                        GetProfileState.Idle -> {}
                        GetProfileState.Loading -> {}
                        is GetProfileState.Success -> {
                            binding.etEmailOrUsername.setText(it.user.email)
                            binding.etEmailOrUsername.setText(it.user.username)
                            binding.etPassword.setText(it.user.password)
                        }
                        GetProfileState.Error -> {}
                    }
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
                        UpdateProfileState.Success -> {
                            findNavController().popBackStack()
                        }
                        UpdateProfileState.Error -> {}
                    }
                }
            }
        }
    }
}