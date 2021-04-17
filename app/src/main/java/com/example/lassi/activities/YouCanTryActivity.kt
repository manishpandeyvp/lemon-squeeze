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
import kotlinx.android.synthetic.main.activity_ingredients_option.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_you_can_try.*

class YouCanTryActivity : AppCompatActivity() {

    private var mJuiceAndShakeList: ArrayList<Juice> = ArrayList()

    private var mSelectedIngredients: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_you_can_try)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val typeFaceRegular : Typeface = Typeface.createFromAsset(assets, "Quicksand-Regular.ttf")
        val typeFaceBold : Typeface = Typeface.createFromAsset(assets, "Quicksand-Bold.ttf")
        val typeFaceSacramento : Typeface = Typeface.createFromAsset(assets, "Sacramento-Regular.ttf")
        tv_you_can_try_title.typeface = typeFaceBold
        tv_try_new_desc.typeface = typeFaceRegular
        tv_try_something_new.typeface = typeFaceSacramento

        mSelectedIngredients = intent.getStringArrayListExtra(Constants.SELECTED_INGREDIENTS_OPTIONS)!!
        Log.i("mSelectedIngredientsY", mSelectedIngredients.toString())

        getJuiceAndShakesList()

        iv_you_can_try_back.setOnClickListener {
            onBackPressed()
        }
    }

    private fun getJuiceAndShakesList(){
        showLoadingGif()
        FireStoreClass().getJuiceAnfShakesList(this)
    }

    fun updateJuiceAndShakesUI(juiceAndShakeList: ArrayList<Juice>){
        hideLoadingGif()
        if(juiceAndShakeList.size > 0) {
            mJuiceAndShakeList = juiceAndShakeList
            rv_you_can_try.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            val adapter = JuiceAndShakeListAdapter(this, juiceAndShakeList)
            rv_popular_item.adapter = adapter
            adapter.setOnClickListener(object : JuiceAndShakeListAdapter.OnClickListener {
                override fun onClick(position: Int, model: Juice) {
                    Log.i("Recipe Id", model.id)
                    val intent = Intent(this@YouCanTryActivity, JuiceAndShakeRecipeActivity::class.java)
                    intent.putExtra(Constants.RECIPE, model)
                    startActivity(intent)
                }
            })
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

    private fun getAvailableOptions(ingredients: ArrayList<String>){

    }
}
