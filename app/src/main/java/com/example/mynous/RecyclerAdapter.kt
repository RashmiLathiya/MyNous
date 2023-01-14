package com.example.mynous

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.mynous.Data.Items

class RecyclerAdapter(
    private val ctx: Context,
    private var lngList: ArrayList<Items>,
) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val itemView = LayoutInflater.from(ctx).inflate(
            R.layout.weather_list,
            parent, false
        )
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val titleString = lngList[position].title
        val id = lngList[position].id.toString()
        val title = lngList[position].title
        val description = lngList[position].description
        val imageUrl = lngList[position].imageUrl

        Glide.with(ctx)
            .load(imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.image)
        holder.id.text = id
        holder.title.text = title
        holder.itemView.setOnClickListener {
                    CustomDialog(ctx, id, titleString!!, description!!, imageUrl!!)
                }
    }

    fun filterList(filterList: ArrayList<Items>) {
        lngList = filterList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return lngList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val id: TextView = itemView.findViewById(R.id.id)
        val title: TextView = itemView.findViewById(R.id.title)
        val image: ImageView = itemView.findViewById(R.id.ivInfo)
    }

}

