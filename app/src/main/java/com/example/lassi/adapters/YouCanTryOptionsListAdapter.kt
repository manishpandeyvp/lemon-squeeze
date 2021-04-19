package com.example.lassi.adapters

import android.content.Context
import android.content.res.AssetManager
import android.graphics.Typeface
import com.bumptech.glide.Glide
import com.example.lassi.R
import com.example.lassi.models.Juice
import kotlinx.android.synthetic.main.item_you_can_try.view.*

open class YouCanTryOptionsListAdapter(
    private var context: Context,
    private val list: ArrayList<Juice>,
    private val assets: AssetManager
): BaseJuiceAdapter(
    R.layout.item_you_can_try,
    list
){

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = list[position]
        Glide.with(context)
            .load(model.image)
            .centerCrop()
            .placeholder(R.drawable.image_placeholder)
            .into(holder.itemView.iv_you_can_try_item)
        holder.itemView.tv_you_can_try_item.text = model.title

        val typeFaceSemiBold : Typeface = Typeface.createFromAsset(assets, "Quicksand-SemiBold.ttf")
        holder.itemView.tv_you_can_try_item.typeface = typeFaceSemiBold

        setOnClickListener{_, _ ->
            onItemClickListener?.let { click ->
                click(position, model)
            }
        }
    }

}

//open class YouCanTryOptionsListAdapter(
//    private val context: Context,
//    private val list: ArrayList<Juice>,
//    private val assets: AssetManager
//) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
//    private var onClickListener: OnClickListener? = null
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_you_can_try, parent, false))
//    }
//
//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        val model = list[position]
//        if(holder is MyViewHolder){
//            Glide.with(context)
//                .load(model.image)
//                .centerCrop()
//                .placeholder(R.drawable.image_placeholder)
//                .into(holder.itemView.iv_you_can_try_item)
//            holder.itemView.tv_you_can_try_item.text = model.title
//
//            val typeFaceRegular : Typeface = Typeface.createFromAsset(assets, "Quicksand-Regular.ttf")
//            val typeFaceSemiBold : Typeface = Typeface.createFromAsset(assets, "Quicksand-SemiBold.ttf")
//            holder.itemView.tv_you_can_try_item.typeface = typeFaceSemiBold
//
//            holder.itemView.setOnClickListener {
//                if(onClickListener != null){
//                    onClickListener!!.onClick(position, model)
//                }
//            }
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return list.size
//    }
//
//    interface OnClickListener{
//        fun onClick(position: Int, model: Juice)
//    }
//
//    fun setOnClickListener(onClickListener: OnClickListener){
//        this.onClickListener = onClickListener
//    }
//
//    private class MyViewHolder(view: View): RecyclerView.ViewHolder(view)
//}