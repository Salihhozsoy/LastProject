package com.example.lastproject.ui.login

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.lastproject.Extensions.showToast
import com.example.lastproject.R
import com.example.lastproject.data.state.LoginState
import com.example.lastproject.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginFragmentViewModel by activityViewModels()

    companion object {
        const val USERID = "userid"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)

        listeners()
        observeLoginState()
        observeMessage()
    }

    private fun observeLoginState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.loginState.collect {
                    when (it) {
                        LoginState.Idle -> {}
                        LoginState.Loading -> {}
                        LoginState.UserNotFound -> {
                            AlertDialog.Builder(requireContext())
                                .setMessage("Kullanıcı Bulunamadı Kayıt sayfasına git")
                                .setPositiveButton(
                                    "Kayıt Ol",
                                    DialogInterface.OnClickListener { dialogInterface, i ->
                                        findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
                                    }).setNegativeButton(
                                "İptal Et",
                                DialogInterface.OnClickListener { dialogInterface, i -> dialogInterface.dismiss() })
                                .create().show()
                        }

                        is LoginState.Success -> {
                            findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment,
                                bundleOf(USERID to it.userId )
                            )
                        }

                        is LoginState.Error -> {}
                    }
                }
            }
        }
    }

    private fun observeMessage() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.message.collect {
                    requireContext().showToast(it)
                }
            }
        }
    }

    private fun listeners() {
        binding.btnLogin.setOnClickListener {
            viewModel.login(
                binding.etEmailOrUsername.text.toString().trim(),
                binding.etPassword.text.toString().trim()
            )
        }
        binding.btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }
}