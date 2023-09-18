package com.example.lastproject.ui.addcategory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.lastproject.Extensions.showAlert
import com.example.lastproject.R
import com.example.lastproject.data.state.AddCategoryState
import com.example.lastproject.databinding.FragmentAddCategoryBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class AddCategoryFragment : Fragment(R.layout.fragment_add_category) {

    private lateinit var binding: FragmentAddCategoryBinding
    private val viewModel: AddCategoryViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddCategoryBinding.bind(view)

        listeners()

    }

    private fun observeAddCategoryState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.addCategoryState.collect {
                    when (it) {
                        AddCategoryState.Idle -> {}
                        AddCategoryState.Empty -> {
                            requireContext().showAlert("Alanı boş bırakmayınız")
                        }

                        AddCategoryState.CategoryAlready -> {
                            requireContext().showAlert("Bu kategori daha önce girilmiş")
                        }

                        AddCategoryState.Result -> {
                            findNavController().navigate(R.id.action_addCategoryFragment_to_dashboardFragment)
                        }

                        is AddCategoryState.Error -> {}
                    }
                }
            }
        }
    }

    private fun listeners() {
        binding.btnAddCategory.setOnClickListener {
            viewModel.addCategory(binding.etCategoryName.text.toString().trim())
        }
    }


}