package com.celenko.digitalguide.ui.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.celenko.digitalguide.data.model.Category
import com.celenko.digitalguide.databinding.ItemCategoriesBinding
import com.squareup.picasso.Picasso

class CategoryAdapter(private val onCategoriesClick: (Category) -> Unit) :
    RecyclerView.Adapter<CategoryAdapter.ItemCategoryDesign>() {

    private val categoryList = ArrayList<Category>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : CategoryAdapter.ItemCategoryDesign {
        val binding =
            ItemCategoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemCategoryDesign(binding)
    }

    override fun onBindViewHolder(holder: CategoryAdapter.ItemCategoryDesign, position: Int) {
        holder.bind(categoryList[position])
    }

    inner class ItemCategoryDesign(private var binding: ItemCategoriesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(categories: Category) {

            with(binding) {
                tvCategory.text = categories.name

                if (categories.image.isNullOrEmpty().not()) {
                    Picasso.get().load(categories.image).into(imgCategories)
                }

                root.setOnClickListener { onCategoriesClick(categories) }
            }
        }
    }

    override fun getItemCount(): Int = categoryList.size

    fun updateList(list: List<Category>) {
        categoryList.clear()
        categoryList.addAll(list)
        notifyItemRangeInserted(0, list.size)
    }
}