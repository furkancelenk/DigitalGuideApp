package com.celenko.digitalguide.ui.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.celenko.digitalguide.databinding.FragmentCategoryBinding
import com.celenko.digitalguide.ui.home.HomeAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment : Fragment() {

    private lateinit var binding: FragmentCategoryBinding

    private val viewModel by viewModels<CategoryViewModel>()

    private val categoryAdapter by lazy {
        CategoryAdapter(
            onCategoriesClick = {
                val action = CategoryFragmentDirections.categoryToHome(it)
                findNavController().navigate(action)
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCategories()

        initObservers()
    }

    private fun initObservers() = with(binding) {
        viewModel.categoryState.observe(viewLifecycleOwner) { state ->

            categoryLoadingView.isVisible = state.isLoading

            state.categoriesList?.let {
                rvCategories.setHasFixedSize(true)
                rvCategories.adapter = categoryAdapter
                categoryAdapter.updateList(state.categoriesList)
            }
        }
    }
}