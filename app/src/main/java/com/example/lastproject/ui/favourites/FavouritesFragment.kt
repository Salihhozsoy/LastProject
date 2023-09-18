package com.example.lastproject.ui.favourites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.lastproject.Extensions.showAlert
import com.example.lastproject.Extensions.showSnackBar
import com.example.lastproject.R
import com.example.lastproject.data.locale.FavouriteEntity
import com.example.lastproject.data.state.FavouriteListState
import com.example.lastproject.databinding.FragmentFavouritesBinding
import com.example.lastproject.ui.adapter.AdapterState
import com.example.lastproject.ui.adapter.FavouriteAdapter
import com.example.lastproject.ui.dashboard.DashboardFragment.Companion.USER

import kotlinx.coroutines.launch


class FavouritesFragment : Fragment(R.layout.fragment_favourites) {

    private lateinit var binding: FragmentFavouritesBinding
    private val viewModel:FavouritesFragmentViewModel by activityViewModels()
    private lateinit var adapter:FavouriteAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentFavouritesBinding.bind(view)

        arguments?.getInt(USER,-1)?.let {
            viewModel.getAllFavourites(it)
        }

        observeFavouriteListState()
        observeAdapterState()
    }

    private fun observeFavouriteListState(){
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                viewModel.favouriteListState.collect{
                    when(it){
                        FavouriteListState.Idle->{}
                        FavouriteListState.Loading->{}
                        FavouriteListState.Empty->{
                            requireContext().showAlert("Favori Listesi Boş")
                        }
                        is FavouriteListState.Success->{
                            adapter = FavouriteAdapter(requireContext(),it.favourites,this@FavouritesFragment::onRemove)
                            binding.rvFavourites.adapter=adapter

                        }
                        is FavouriteListState.Error->{}
                    }
                }
            }
        }
    }
    private fun observeAdapterState(){
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                viewModel.adapterState.collect{
                    when(it){
                      AdapterState.Idle->{}
                      is AdapterState.Remove->{
                          adapter.notifyDataSetChanged()
                          requireContext().showSnackBar(binding.rvFavourites,"Favorilerden Silindi")
                      }
                      AdapterState.Error->{}
                    }
                }
            }
        }
    }

    private fun onRemove(position:Int){
        viewModel.removeFromFavourite(position)
    }

}