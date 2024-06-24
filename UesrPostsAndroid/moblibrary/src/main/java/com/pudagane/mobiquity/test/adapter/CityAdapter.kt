package com.pudagane.mobiquity.test.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pudagane.mobiquity.test.R
import com.pudagane.mobiquity.test.model.City
import com.pudagane.mobiquity.test.ui.details.CityListViewModel
import kotlinx.android.synthetic.main.city_list_item.view.*

class CityAdapter (private val cityListViewModel: CityListViewModel, private val cities: List<City>?) : RecyclerView.Adapter<CityAdapter.ViewHolder>(){
    var onItemClick: ((String) -> Unit)? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, index: Int): ViewHolder {
        val rootView = LayoutInflater.from(viewGroup.context).inflate(R.layout.city_list_item, viewGroup, false)
        return ViewHolder(rootView)
    }

    override fun getItemCount(): Int {
            return cities?.size!!
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, index: Int) {
        viewHolder.messageTV.text = cities?.get(index)?.cityName
        viewHolder.ivLogo.setOnClickListener {
            cityListViewModel.remove(cities?.get(index)?.id!!)
        }


    }


    inner class ViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView)
    {
         val messageTV: TextView = itemView.tvCityName
         val ivLogo:ImageView = itemView.deleteLogo

        init {
            itemView.setOnClickListener {
                cities?.get(position)?.cityName?.let { it1 -> onItemClick?.invoke(it1) }
            }
        }
    }

}