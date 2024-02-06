package com.celenko.digitalguide.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.celenko.digitalguide.common.showSnackbar
import com.celenko.digitalguide.databinding.FragmentFavoritesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding

    private val viewModel by viewModels<FavoritesViewModel>()

    private val favoritesAdapter by lazy {
        FavoritesAdapter(
            onRemoveFavoriteClick = {
                viewModel.deleteFavorites(it)
                requireView().showSnackbar("Favorilerden silindi.")
            },
            onPlacesClick = {
                val action = FavoritesFragmentDirections.favoritesToDetail(it)
                findNavController().navigate(action)
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getFavorites()

        initObservers()
    }

    private fun initObservers() = with(binding) {
        viewModel.favoritesState.observe(viewLifecycleOwner) { state ->

            with(state) {
                if (favoritesList.isNullOrEmpty()) {
                    rvFavorites.visibility = View.GONE
                    favoritesEmptyView.visibility = View.VISIBLE
                    favoritesEmptyText.visibility = View.VISIBLE
                } else {
                    favoritesList.let {
                        favoritesEmptyView.visibility = View.GONE
                        favoritesEmptyText.visibility = View.GONE
                        rvFavorites.adapter = favoritesAdapter
                        rvFavorites.setHasFixedSize(true)
                        favoritesAdapter.updateList(it)
                    }
                }
                errorMessage?.let { requireView().showSnackbar("Error") }
            }
        }
    }
}