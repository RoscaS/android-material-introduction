package com.sol.android.travelwishlist.adapters

import android.content.Context
import android.graphics.BitmapFactory
import android.support.v4.content.ContextCompat
import android.support.v7.graphics.Palette
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.sol.android.travelwishlist.PlaceData
import com.sol.android.travelwishlist.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_places.view.*

class TravelListAdapter(private var context: Context) :
        RecyclerView.Adapter<TravelListAdapter.ViewHolder>() {

  /*------------------------------------------------------------------*\
  |*							                ATTRIBUTES
  \*------------------------------------------------------------------*/

  lateinit var itemClickListener: OnItemClickListener

  /*------------------------------------------------------------------*\
  |*							                HOOKS
  \*------------------------------------------------------------------*/

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val itemView =
      LayoutInflater.from(parent.context).inflate(R.layout.row_places, parent, false)
    return ViewHolder(itemView)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val place = PlaceData.placeList().get(position)
    holder.itemView.placeName.text = place.name

    Picasso.with(context)
            .load(place.getImageResourceId(context))
            .into(holder.itemView.placeImage)

    val imageId = place.getImageResourceId(context)
    val photo = BitmapFactory.decodeResource(context.resources, imageId)

    Palette.from(photo).generate { palette ->
      val black = ContextCompat.getColor(context, android.R.color.black)

      // val bgColor = palette.getMutedColor(black)
      val bgColor = palette.getVibrantColor(black)

      holder.itemView.placeNameHolder.setBackgroundColor(bgColor)
    }
  }

  /*------------------------------------------------------------------*\
  |*							                METHODES
  \*------------------------------------------------------------------*/

  fun setOnItemClickListener(itemClickListener: OnItemClickListener) {
    this.itemClickListener = itemClickListener
  }

  override fun getItemCount() = PlaceData.placeList().size

  /*------------------------------------------------------------------*\
  |*							                INNER
  \*------------------------------------------------------------------*/

  /*------------------------------*\
  |*			        classes
  \*------------------------------*/

  inner class ViewHolder(itemView: View) :
          RecyclerView.ViewHolder(itemView), View.OnClickListener {

    init {
      itemView.placeHolder.setOnClickListener(this)
    }

    override fun onClick(p0: View?) =
      itemClickListener.onItemClick(itemView, adapterPosition)
  }

  /*------------------------------*\
  |*			        interfaces
  \*------------------------------*/

  interface OnItemClickListener {
    fun onItemClick(view: View, position: Int)
  }

}