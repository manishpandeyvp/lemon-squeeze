package com.example.lassi.adapters

import android.content.Context
import android.content.res.AssetManager
import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lassi.R
import kotlinx.android.synthetic.main.item_ingredient_option.view.*

open class IngredientOptionsListAdapter(
    private val context: Context,
    private val list: ArrayList<String>,
    private val assets: AssetManager
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mSelectedList: ArrayList<String> = ArrayList()

    private var onClickListener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_ingredient_option, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val ingredient = list[position]
        if(holder is MyViewHolder){
            holder.itemView.cb_ingredients_option.text = ingredient
            val typeFaceRegular : Typeface = Typeface.createFromAsset(assets, "Quicksand-Regular.ttf")
            holder.itemView.cb_ingredients_option.typeface = typeFaceRegular

            holder.itemView.cb_ingredients_option.setOnClickListener {
                if(holder.itemView.cb_ingredients_option.isChecked){
                    mSelectedList.add(ingredient)
                    Log.i("mOptionHolderA", mSelectedList.toString())
                }else{
                    mSelectedList.remove(ingredient)
                    Log.i("mOptionHolderB", mSelectedList.toString())
                }
                if(onClickListener != null){
                    onClickListener!!.onClick(position, ingredient, mSelectedList)
                }
            }

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnClickListener{
        fun onClick(position: Int, ingredient: String, selectedList: ArrayList<String>)
    }

    fun setOnClickListener(onClickListener: OnClickListener){
        this.onClickListener = onClickListener
    }

    private class MyViewHolder(view: View): RecyclerView.ViewHolder(view)
}