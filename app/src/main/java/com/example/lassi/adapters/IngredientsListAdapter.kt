package com.example.lassi.adapters

import android.content.Context
import android.content.res.AssetManager
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lassi.R
import kotlinx.android.synthetic.main.item_ingredient.view.*

open class IngredientsListAdapter(
    private val context: Context,
    private val list: ArrayList<String>,
    private val assets: AssetManager
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_ingredient, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val ingredient = list[position]
        if(holder is MyViewHolder){
            holder.itemView.tv_ingredient_sr_no.text = "${position+1}. "
            holder.itemView.tv_ingredient.text = ingredient
            val typeFaceRegular : Typeface = Typeface.createFromAsset(assets, "Quicksand-Regular.ttf")
            val typeFaceSemiBold : Typeface = Typeface.createFromAsset(assets, "Quicksand-SemiBold.ttf")
            holder.itemView.tv_ingredient.typeface = typeFaceRegular
            holder.itemView.tv_ingredient_sr_no.typeface = typeFaceSemiBold
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    private class MyViewHolder(view: View): RecyclerView.ViewHolder(view)
}