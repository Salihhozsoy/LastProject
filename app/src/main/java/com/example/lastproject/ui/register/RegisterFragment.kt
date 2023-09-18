package com.example.lastproject.ui.register

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
import com.example.lastproject.Extensions.showAlert
import com.example.lastproject.Extensions.showToast
import com.example.lastproject.R
import com.example.lastproject.data.state.RegisterState
import com.example.lastproject.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : Fragment(R.layout.fragment_register) {
    private lateinit var binding: FragmentRegisterBinding
    private val viewModel:RegisterFragmentViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentRegisterBinding.bind(view)

        observeRegisterState()
        listeners()
        observeMessage()
    }


    private fun observeRegisterState(){
        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.CREATED){
                viewModel.registerState.collect{
                    when(it){
                        RegisterState.Idle->{}
                        RegisterState.UserAlready->{}
                        RegisterState.Loading->{}
                        is RegisterState.Result->{
                            findNavController().navigate(R.id.action_registerFragment_to_dashboardFragment)
                        }
                        is RegisterState.Error->{}
                    }
                }
            }
        }
    }
    private fun observeMessage(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                viewModel.message.collect{
                    requireContext().showAlert(it)
                }
            }
        }
    }
    private fun listeners(){
        binding.btnRegister.setOnClickListener {
            viewModel.register(binding.etRegisterEmailOrUsername.text.toString().trim(),binding.etRegisterPassword.text.toString().trim(),binding.etConfirmPassword.text.toString().trim())
        }
        binding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }
}