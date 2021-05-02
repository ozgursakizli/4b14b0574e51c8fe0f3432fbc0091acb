package com.ozgursakizli.starshipdelivery.ui.stations

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ozgursakizli.starshipdelivery.databinding.ItemStationBinding
import com.ozgursakizli.starshipdelivery.models.ApiSpaceStationModel


class StationsAdapter constructor(
    private val onItemClickListener: ItemClickListener
) : RecyclerView.Adapter<StationsAdapter.StationViewHolder>() {

    private var stationList: MutableList<ApiSpaceStationModel> = arrayListOf()
    private lateinit var currentStation: ApiSpaceStationModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationsAdapter.StationViewHolder {
        val itemBinding = ItemStationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StationViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: StationsAdapter.StationViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemCount(): Int = stationList.size

    private fun getItem(position: Int): ApiSpaceStationModel = stationList[position]

    fun filterStation(station: ApiSpaceStationModel?) {
        station?.let {
            this.stationList.clear()
            this.stationList.add(station)
            notifyDataSetChanged()
        }
    }

    fun setData(list: MutableList<ApiSpaceStationModel>, currentStation: ApiSpaceStationModel?) {
        this.stationList.clear()
        this.stationList.addAll(list)
        currentStation?.let {
            this.currentStation = currentStation
            this.stationList.contains(currentStation).apply { stationList.remove(currentStation) }
        }
        notifyDataSetChanged()
    }

    inner class StationViewHolder(private val itemBinding: ItemStationBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        init {
            itemBinding.apply {
                btnTravel.setOnClickListener { onItemClickListener.onTravelClicked(getItem(adapterPosition)) }
                imgFavourite.setOnClickListener { onItemClickListener.onFavouriteClicked(getItem(adapterPosition)) }
            }
        }

        fun bind(data: ApiSpaceStationModel) {
            itemBinding.apply {
                tvStationName.text = data.name
            }
        }
    }

    interface ItemClickListener {
        fun onTravelClicked(station: ApiSpaceStationModel)
        fun onFavouriteClicked(station: ApiSpaceStationModel)
    }

}