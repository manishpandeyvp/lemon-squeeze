package com.example.lassi.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lassi.models.Juice

abstract class BaseJuiceAdapter(
    private val layoutId: Int,
    private val list: ArrayList<Juice>
) : RecyclerView.Adapter<BaseJuiceAdapter.MyViewHolder>(){

    var onClickListener: OnClickListener? = null

    class MyViewHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnClickListener{
        fun onClick(position: Int, model: Juice)
    }

//    protected var onItemClickListener: ((position: Int, model: Juice) -> Unit)? = null
//
//    fun setOnClickListener(onClickListener: (position: Int, model: Juice) -> Unit){
//        this.onItemClickListener = onClickListener
//    }
}