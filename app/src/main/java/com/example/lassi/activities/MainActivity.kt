package com.example.lassi.activities

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lassi.R
import com.example.lassi.adapters.JuiceAndShakeListAdapter
import com.example.lassi.firebase.FireStoreClass
import com.example.lassi.models.Juice
import com.example.lassi.utils.Constants
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val typeFaceRegular : Typeface = Typeface.createFromAsset(assets, "Quicksand-Regular.ttf")
        val typeFaceBold : Typeface = Typeface.createFromAsset(assets, "Quicksand-Bold.ttf")
        val typeFaceSemiBold : Typeface = Typeface.createFromAsset(assets, "Quicksand-SemiBold.ttf")
        val typeFaceSacramento : Typeface = Typeface.createFromAsset(assets, "Sacramento-Regular.ttf")
        et_search.typeface = typeFaceRegular
        tv_all.typeface = typeFaceSemiBold
        tv_cold_shakes.typeface = typeFaceSemiBold
        tv_juices.typeface = typeFaceSemiBold
        tv_wishes.typeface = typeFaceBold
        tv_try_new.typeface = typeFaceSacramento

        displayWishes()
        getJuiceAndShakesList()
    }

    private fun getJuiceAndShakesList(){
        showLoadingGif()
        FireStoreClass().getJuiceAnfShakesList(this)
    }

    fun updateJuiceAndShakesUI(juiceAndShakeList: ArrayList<Juice>){
        hideLoadingGif()
        if(juiceAndShakeList.size > 0){
            rv_popular_item.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            val adapter = JuiceAndShakeListAdapter(this, juiceAndShakeList)
            rv_popular_item.adapter = adapter
            adapter.setOnClickListener(object : JuiceAndShakeListAdapter.OnClickListener{
                override fun onClick(position: Int, model: Juice) {
                    Log.i("Recipe Id", model.id)
                    val intent = Intent(this@MainActivity, JuiceAndShakeRecipeActivity::class.java)
                    intent.putExtra(Constants.RECIPE, model)
                    startActivity(intent)
                }
            })
        }
    }

    private fun displayWishes(){
        val c = Calendar.getInstance().time
        val sdf = SimpleDateFormat("HH", Locale.getDefault())
        val curTime = sdf.format(c)
        Log.i("Current Time", curTime)
        if(curTime.toInt() < 12){
            tv_wishes.text = "Good Morning :D"
        }else if (curTime.toInt() in 12..15){
            tv_wishes.text = "Good Afternoon :D"
        }else{
            tv_wishes.text ="Good Evening :D"
        }
    }

    private fun showLoadingGif(){
        gif_loading.visibility = View.VISIBLE
        ll_popular_item.visibility = View.GONE
    }

    fun hideLoadingGif(){
        gif_loading.visibility = View.GONE
        ll_popular_item.visibility = View.VISIBLE
    }
}
