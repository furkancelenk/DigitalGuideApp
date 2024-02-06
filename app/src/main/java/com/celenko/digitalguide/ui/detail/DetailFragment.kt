package com.celenko.digitalguide.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.celenko.digitalguide.R
import com.celenko.digitalguide.common.showSnackbar
import com.celenko.digitalguide.databinding.FragmentDetailBinding
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding

    private val viewModel by viewModels<DetailViewModel>()

    private val args by navArgs<DetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getPlaceDetail(args.id)

        with(binding) {

            buttonAddFavorites.setOnClickListener {
                viewModel.setFavoriteState()
            }

            buttonBackDetail.setOnClickListener { findNavController().navigateUp() }
        }

        initObservers()
    }

    private fun initObservers() = with(binding) {

        viewModel.placeState.observe(viewLifecycleOwner) { state ->

            detailLoadingView.isVisible = state.isLoading

            state.places?.let {
                Picasso.get().load(it.imageUrl).into(imgDetail)
                tvDetailTitle.text = it.title
                tvDetailLocation.text = it.location
                tvDetailDescription.text = it.description

                buttonAddFavorites.setImageResource(
                    if (it.isFavorite) {
                        R.drawable.ic_favorites_selected
                    } else {
                        R.drawable.ic_favorites_unselected
                    }
                )
            }
        }
    }
}
