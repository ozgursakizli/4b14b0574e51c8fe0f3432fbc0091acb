package com.ozgursakizli.starshipdelivery.ui.favourites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ozgursakizli.starshipdelivery.database.stations.StationEntity
import com.ozgursakizli.starshipdelivery.databinding.ItemFavouriteStationBinding

class FavouriteStationsAdapter constructor(
    private val onItemClickListener: ItemClickListener
) : RecyclerView.Adapter<FavouriteStationsAdapter.FavouriteStationHolder>() {

    private var stationList: MutableList<StationEntity> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteStationHolder {
        val itemBinding = ItemFavouriteStationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavouriteStationHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: FavouriteStationHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemCount(): Int = stationList.size

    private fun getItem(position: Int): StationEntity = stationList[position]

    fun setData(list: MutableList<StationEntity>) {
        this.stationList.clear()
        this.stationList.addAll(list)
        notifyDataSetChanged()
    }

    inner class FavouriteStationHolder(private val itemBinding: ItemFavouriteStationBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        init {
            itemBinding.apply {
                root.setOnClickListener { onItemClickListener.onItemClicked(getItem(adapterPosition)) }
            }
        }

        fun bind(data: StationEntity) {
            itemBinding.apply {
                tvStationName.text = data.name
            }
        }
    }

    interface ItemClickListener {
        fun onItemClicked(stationEntity: StationEntity)
    }

}