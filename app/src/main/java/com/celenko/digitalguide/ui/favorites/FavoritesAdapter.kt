package com.celenko.digitalguide.ui.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.celenko.digitalguide.data.model.Places
import com.celenko.digitalguide.databinding.ItemFavoritesBinding
import com.squareup.picasso.Picasso

class FavoritesAdapter(
    private val onPlacesClick: (Int) -> Unit,
    private val onRemoveFavoriteClick: (Int) -> Unit
) : RecyclerView.Adapter<FavoritesAdapter.FavoritesItemDesign>() {

    private val favoritesList = ArrayList<Places>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoritesItemDesign {
        val binding =
            ItemFavoritesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoritesItemDesign(binding)
    }

    override fun onBindViewHolder(holder: FavoritesItemDesign, position: Int) {
        holder.bind(favoritesList[position])
    }

    inner class FavoritesItemDesign(private var binding: ItemFavoritesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(places: Places) {
            with(binding) {

                tvFavoritesTitle.text = places.title
                tvFavoritesLocation.text = places.location
                tvFavoritesDescription.text = places.description

                places.imageUrl.let {
                    Picasso.get().load(it).into(imgFavoritesCard)
                }

                imgFavoritesHeart.setOnClickListener {
                    onRemoveFavoriteClick(places.id ?: 1)
                }

                root.setOnClickListener { onPlacesClick(places.id ?: 1) }
            }
        }
    }

    override fun getItemCount(): Int {
        return favoritesList.size
    }

    fun updateList(list: List<Places>) {
        favoritesList.clear()
        favoritesList.addAll(list)
        notifyItemRangeInserted(0, list.size)
    }
}