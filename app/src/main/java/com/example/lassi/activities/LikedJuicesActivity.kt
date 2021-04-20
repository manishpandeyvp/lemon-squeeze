package com.example.lassi.activities

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lassi.R
import com.example.lassi.adapters.SavedLikedItemsAdapter
import com.example.lassi.firebase.FireStoreClass
import com.example.lassi.models.Juice
import com.example.lassi.utils.Constants
import kotlinx.android.synthetic.main.activity_liked_juices.*
import kotlinx.android.synthetic.main.activity_liked_juices.gif_nothing_found
import kotlinx.android.synthetic.main.activity_liked_juices.tv_nothing_found

class LikedJuicesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_liked_juices)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val typeFaceBold : Typeface = Typeface.createFromAsset(assets, "Quicksand-Bold.ttf")
        val typeFaceSemiBold : Typeface = Typeface.createFromAsset(assets, "Quicksand-SemiBold.ttf")

        tv_liked_title.typeface = typeFaceBold
        tv_nothing_found.typeface = typeFaceSemiBold

        iv_liked_back.setOnClickListener {
            onBackPressed()
        }

        getLikedJuicesList()
    }

    private fun updateJuiceAndShakesUI(juiceAndShakeList: ArrayList<Juice>){
        if(juiceAndShakeList.size > 0) {
            gif_nothing_found.visibility = View.GONE
            tv_nothing_found.visibility = View.GONE
            rv_liked.layoutManager =
                LinearLayoutManager(this)
            val adapter = SavedLikedItemsAdapter(this, juiceAndShakeList, assets)
            rv_liked.adapter = adapter
            adapter.setOnClickListener(object : SavedLikedItemsAdapter.OnClickListener {
                override fun onClick(position: Int, model: Juice) {
                    val intent = Intent(this@LikedJuicesActivity, JuiceAndShakeRecipeActivity::class.java)
                    intent.putExtra(Constants.RECIPE, model)
                    startActivity(intent)
                }
            })

        }else{
            rv_liked.visibility = View.GONE
            gif_nothing_found.visibility = View.VISIBLE
            tv_nothing_found.visibility = View.VISIBLE
        }
    }

    private fun getLikedJuicesList(){
        FireStoreClass().getSavedLikedJuices(this)
    }

    fun getLikedJuicesListSuccess(mLikedJuices: ArrayList<Juice>){
        updateJuiceAndShakesUI(mLikedJuices)
    }

    override fun onStart() {
        super.onStart()
        if(Constants.user_data.likedJuices.size>0){
            getLikedJuicesList()
        }else{
            updateJuiceAndShakesUI(ArrayList())
        }
    }
}
