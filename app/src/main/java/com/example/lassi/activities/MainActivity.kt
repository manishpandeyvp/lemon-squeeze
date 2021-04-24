package com.example.lassi.activities

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.lassi.R
import com.example.lassi.adapters.JuiceAndShakeListAdapter
import com.example.lassi.firebase.FireStoreClass
import com.example.lassi.models.Juice
import com.example.lassi.models.User
import com.example.lassi.utils.Constants
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private var mJuiceAndShakeList: ArrayList<Juice> = ArrayList()
    private var mSearchedResult: ArrayList<Juice> = ArrayList()

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
        tv_randomly_picked_title.typeface = typeFaceBold
        tv_randomly_picked.typeface = typeFaceSemiBold
        tv_try_this_out.typeface  =typeFaceRegular

        displayWishes()
        getJuiceAndShakesList()

        iv_drawer.setOnClickListener {
            startActivity(Intent(this, OptionsDrawerActivity::class.java))
        }

        cv_try_new.setOnClickListener {
            val intent = Intent(this, IngredientsOption::class.java)
            intent.putExtra(Constants.JUICE_AND_SHAKES_LIST, mJuiceAndShakeList)
            startActivity(intent)
        }

        val randomValue = (rand() * (mJuiceAndShakeList.size+1)).toInt() + 1

        gif_loading_randomly_picked.visibility = View.VISIBLE
        ll_randomly_picked.visibility = View.GONE

        Handler().postDelayed({
            gif_loading_randomly_picked.visibility = View.GONE
            ll_randomly_picked.visibility = View.VISIBLE
            Glide.with(this)
                .load(mJuiceAndShakeList[randomValue].image)
                .centerCrop()
                .placeholder(R.drawable.image_placeholder)
                .into(iv_randomly_picked)
            tv_randomly_picked.text = mJuiceAndShakeList[randomValue].title
        }, 5000)

        ll_randomly_picked.setOnClickListener {
            val intent = Intent(this@MainActivity, JuiceAndShakeRecipeActivity::class.java)
            intent.putExtra(Constants.RECIPE, mJuiceAndShakeList[randomValue])
            startActivity(intent)
        }

        iv_search.setOnClickListener {
            val searchedText: String = et_search.text.toString()
            if(searchedText.isNotEmpty()){
                searchJuices(searchedText)
                val intent = Intent(this, SearchResultsActivity::class.java)
                intent.putExtra(Constants.SEARCHED_RESULTS, mSearchedResult)
                intent.putExtra(Constants.SEARCHED_STRING, searchedText)
                startActivity(intent)
            }else{
                val snackBar = Snackbar.make(it, "Please enter some text", Snackbar.LENGTH_SHORT)
                snackBar.setBackgroundTint(Color.parseColor("#206a5d"))
                snackBar.setActionTextColor(Color.parseColor("#d5ecc2"))
                snackBar.show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        checkUserSignedIn()
        getJuiceAndShakesList()
    }

    private fun checkUserSignedIn(){
        if(FireStoreClass().getCurrentUserId().isNotEmpty()){
            FireStoreClass().getUserData(this)
        }
    }

    fun updateUserData(mUserData: User){
        Constants.user_data = mUserData
        Log.i("user_data", Constants.user_data.toString())
    }

    private fun getJuiceAndShakesList(){
        showLoadingGif()
        FireStoreClass().getJuiceAnfShakesList(this)
    }

    fun updateJuiceAndShakesUI(juiceAndShakeList: ArrayList<Juice>){
        hideLoadingGif()
        if(juiceAndShakeList.size > 0) {
            mJuiceAndShakeList = juiceAndShakeList
            rv_popular_item.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            val adapter = JuiceAndShakeListAdapter(this, juiceAndShakeList, assets)
            rv_popular_item.adapter = adapter
            adapter.setOnClickListener(object : JuiceAndShakeListAdapter.OnClickListener {
                override fun onClick(position: Int, model: Juice) {
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

    private fun rand(): Double {
        return Math.random()
    }

    private fun searchJuices(s: String){
        mSearchedResult = ArrayList()
        for (i in mJuiceAndShakeList){
            val list = i.title.split(" ")
            if(s.toLowerCase(Locale.ROOT) == i.title.toLowerCase(Locale.ROOT)) {
                mSearchedResult.add(i)
            }
            for (j in list){
                if(s.toLowerCase(Locale.ROOT) == j.toLowerCase(Locale.ROOT)) {
                    if(!mSearchedResult.contains(i)){
                        mSearchedResult.add(i)
                    }
                }
            }
        }
    }
}
