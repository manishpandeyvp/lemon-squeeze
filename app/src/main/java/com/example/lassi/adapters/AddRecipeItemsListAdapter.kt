package com.example.lassi.adapters

import android.content.Context
import android.content.res.AssetManager
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lassi.R
import kotlinx.android.synthetic.main.item_edit_recipe.view.*

class AddRecipeItemsListAdapter (
    private val context: Context,
    private val list: ArrayList<String>,
    private val assets: AssetManager
): RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private var onClickListener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_edit_recipe, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val recipe = list[position]
        if(holder is MyViewHolder){
            holder.itemView.tv_edit_recipe_item.text = recipe
            val typeFaceRegular : Typeface = Typeface.createFromAsset(assets, "Quicksand-Regular.ttf")
            holder.itemView.tv_edit_recipe_item.typeface = typeFaceRegular

            holder.itemView.iv_delete_recipe_item.setOnClickListener {
                if(onClickListener != null){
                    onClickListener!!.onClick(position, recipe)
                }
            }

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnClickListener{
        fun onClick(position: Int, ingredient: String)
    }

    fun setOnClickListener(onClickListener: OnClickListener){
        this.onClickListener = onClickListener
    }

    private class MyViewHolder(view: View): RecyclerView.ViewHolder(view)
}