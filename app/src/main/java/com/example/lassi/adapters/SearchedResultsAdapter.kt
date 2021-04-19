package com.example.lassi.adapters

import android.content.Context
import android.content.res.AssetManager
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lassi.R
import com.example.lassi.models.Juice
import kotlinx.android.synthetic.main.item_juice_card.view.*
import kotlinx.android.synthetic.main.item_search_result.view.*

open class SearchedResultsAdapter(
    private val context: Context,
    private val list: ArrayList<Juice>,
    private val assets: AssetManager
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private var onClickListener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_search_result, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = list[position]
        if(holder is MyViewHolder){
            Glide.with(context)
                .load(model.image)
                .centerCrop()
                .placeholder(R.drawable.image_placeholder)
                .into(holder.itemView.iv_search_result_item)
            holder.itemView.tv_search_result_item_title.text = model.title

            val typeFaceRegular : Typeface = Typeface.createFromAsset(assets, "Quicksand-Regular.ttf")
            val typeFaceSemiBold : Typeface = Typeface.createFromAsset(assets, "Quicksand-SemiBold.ttf")
            holder.itemView.tv_search_result_item_title.typeface = typeFaceSemiBold
            holder.itemView.tv_search_result_try_this_out.typeface = typeFaceRegular

            holder.itemView.setOnClickListener {
                if(onClickListener != null){
                    onClickListener!!.onClick(position, model)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnClickListener{
        fun onClick(position: Int, model: Juice)
    }

    fun setOnClickListener(onClickListener: OnClickListener){
        this.onClickListener = onClickListener
    }

    private class MyViewHolder(view: View): RecyclerView.ViewHolder(view)
}