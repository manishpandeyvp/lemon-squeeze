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
import com.example.lassi.adapters.YouCanTryOptionsListAdapter
import com.example.lassi.firebase.FireStoreClass
import com.example.lassi.models.Juice
import com.example.lassi.utils.Constants
import kotlinx.android.synthetic.main.activity_you_can_try.*

class YouCanTryActivity : AppCompatActivity() {

    private var mJuiceAndShakeList: ArrayList<Juice> = ArrayList()
    private var mAvailableOptionsList: ArrayList<Juice> = ArrayList()
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
        tv_nothing_found.typeface = typeFaceRegular

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
            getAvailableOptions(mSelectedIngredients, mJuiceAndShakeList)
            Log.i("AvailableOptions", mAvailableOptionsList.toString())

            if(mAvailableOptionsList.isNotEmpty()) {
                gif_nothing_found.visibility = View.GONE
                tv_nothing_found.visibility = View.GONE
                ll_you_can_try.visibility = View.VISIBLE

                rv_you_can_try.layoutManager =
                    LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                val adapter = YouCanTryOptionsListAdapter(this, mAvailableOptionsList, assets)
                rv_you_can_try.adapter = adapter

                adapter.setOnClickListener(object : YouCanTryOptionsListAdapter.OnClickListener {
                    override fun onClick(position: Int, model: Juice) {
                        Log.i("Recipe Id", model.id)
                        val intent = Intent(this@YouCanTryActivity, JuiceAndShakeRecipeActivity::class.java)
                        intent.putExtra(Constants.RECIPE, model)
                        startActivity(intent)
                    }
                })
            }else{
                gif_nothing_found.visibility = View.VISIBLE
                tv_nothing_found.visibility = View.VISIBLE
                ll_you_can_try.visibility = View.GONE
            }
        }
    }

    private fun showLoadingGif(){
        gif_loading_you_can_try.visibility = View.VISIBLE
        ll_you_can_try.visibility = View.GONE
    }

    fun hideLoadingGif(){
        gif_loading_you_can_try.visibility = View.GONE
        ll_you_can_try.visibility = View.VISIBLE
    }

    private fun getAvailableOptions(selectedIngredients: ArrayList<String>, juiceAndShakeList: ArrayList<Juice>){
        for(i in juiceAndShakeList){
            var x = 0
            for (j in i.ingredients){
                if(selectedIngredients.contains(j)){
                    x += 1
                }
            }
            if(i.ingredients.size > 5){
                if (x >= i.ingredients.size-2){
                    mAvailableOptionsList.add(i)
                }
            } else if(i.ingredients.size in 3..5){
                if (x >= i.ingredients.size-1){
                    mAvailableOptionsList.add(i)
                }
            }else{
                if (x == i.ingredients.size){
                    mAvailableOptionsList.add(i)
                }
            }
        }
    }
}
