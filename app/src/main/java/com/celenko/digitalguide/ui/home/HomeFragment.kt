package com.celenko.digitalguide.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.celenko.digitalguide.common.showSnackbar
import com.celenko.digitalguide.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<HomeViewModel>()

    private val args by navArgs<HomeFragmentArgs>()

    private val homeAdapter by lazy {
        HomeAdapter(
            onPlacesClick = {
                val action = HomeFragmentDirections.homeToDetail(it)
                findNavController().navigate(action)
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val category = args.category.name
        category?.takeIf { it.isNotBlank() }?.let {
            viewModel.getPlacesWithCategory(it)
        }

        initObserver()
    }

    private fun initObserver() = with(binding) {
        viewModel.placesState.observe(viewLifecycleOwner) { state ->

            homeLoadingView.isVisible = state.isLoading

            state.placesList?.let {
                rvPlaces.setHasFixedSize(true)
                rvPlaces.adapter = homeAdapter
                homeAdapter.updateList(state.placesList)
            }

            state.errorMessage?.let {
                requireView().showSnackbar("Empty List")
            }

            state.failMessage?.let {
                requireView().showSnackbar("Empty List")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}