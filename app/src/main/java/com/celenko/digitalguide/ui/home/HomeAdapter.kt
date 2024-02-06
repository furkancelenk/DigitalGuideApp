package com.celenko.digitalguide.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.celenko.digitalguide.data.model.Places
import com.celenko.digitalguide.databinding.ItemHomeBinding
import com.squareup.picasso.Picasso

class HomeAdapter(
    private val onPlacesClick: (Int) -> Unit,
) : RecyclerView.Adapter<HomeAdapter.CardViewDesign>() {

    private val placesList = ArrayList<Places>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewDesign {
        val binding =
            ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardViewDesign(binding)
    }

    override fun onBindViewHolder(holder: CardViewDesign, position: Int) =
        holder.bind(placesList[position])


    inner class CardViewDesign(private var binding: ItemHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(places: Places) {

            with(binding) {

                tvPlaceTitle.text = places.title
                ratingBar.rating = places.rate!!.toFloat()
                tvHomeLocation.text = places.location

                places.imageUrl?.let {
                    Picasso.get().load(it).into(imgHomeCard)
                }

                root.setOnClickListener {
                    onPlacesClick(places.id ?: 0)
                }
            }
        }
    }

    override fun getItemCount(): Int = placesList.size

    fun updateList(list: List<Places>) {
        placesList.clear()
        placesList.addAll(list)
        notifyItemRangeInserted(0, list.size)
    }
}