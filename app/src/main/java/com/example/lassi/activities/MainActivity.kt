package com.example.lassi.activities

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lassi.R
import com.example.lassi.adapters.JuiceAndShakeListAdapter
import com.example.lassi.firebase.FireStoreClass
import com.example.lassi.models.Juice
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

        ll_new_arrival.setOnClickListener {
            startActivity(Intent(this, JuiceAndShakeRecipeActivity::class.java))
        }
    }

    private fun getJuiceAndShakesList(){
        FireStoreClass().getJuiceAnfShakesList(this)
    }

    fun updateJuiceAndShakesUI(juiceAndShakeList: ArrayList<Juice>){
        if(juiceAndShakeList.size > 0){
            rv_popular_item.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            val adapter = JuiceAndShakeListAdapter(this, juiceAndShakeList)
            rv_popular_item.adapter = adapter
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
}
