package com.example.lassi.adapters

import android.content.Context
import android.content.res.AssetManager
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lassi.models.Juice

open class SavedLikedItemsAdapter(
    private val context: Context,
    private val list: ArrayList<Juice>,
    private val assets: AssetManager
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}