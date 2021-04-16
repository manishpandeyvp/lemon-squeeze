package com.example.lassi.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lassi.R
import com.example.lassi.adapters.IngredientOptionsListAdapter
import com.example.lassi.models.Juice
import com.example.lassi.utils.Constants
import kotlinx.android.synthetic.main.activity_ingredients_option.*

class IngredientsOption : AppCompatActivity() {

    private lateinit var mJuiceAndShakesList: ArrayList<Juice>
    private var mIngredients: ArrayList<String> = ArrayList()
    private var mSelectedIngredients: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingredients_option)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        mJuiceAndShakesList = intent.getParcelableArrayListExtra(Constants.JUICE_AND_SHAKES_LIST)!!
        Log.i("mJuiceAndShakesList", mJuiceAndShakesList.toString())

        getIngredientsList()
        Log.i("mIngredients", mIngredients.toString())

        updateIngredientsOptionsList()
        checkProceedingAvailability()
    }

    private fun getIngredientsList(){
        for (i in mJuiceAndShakesList){
            Log.i("mIngredientsList", i.ingredients.toString())
            for (j in i.ingredients){
                Log.i("Value of j", j)
                if (!mIngredients.contains(j)){
                    mIngredients.add(j)
                }
            }
        }
    }

    private fun updateIngredientsOptionsList(){
        rv_ingredients_option.layoutManager = LinearLayoutManager(this)
        val ingredientsAdapter = IngredientOptionsListAdapter(this, mIngredients, assets)
        rv_ingredients_option.setHasFixedSize(true)
        rv_ingredients_option.adapter = ingredientsAdapter

        ingredientsAdapter.setOnClickListener(object :
            IngredientOptionsListAdapter.OnClickListener {
            override fun onClick(position: Int, ingredient: String, selectedList: ArrayList<String>) {
                Log.i("mSelectedIngredients", selectedList.toString())
                mSelectedIngredients = selectedList
                checkProceedingAvailability()
            }
        })
    }

    private fun checkProceedingAvailability(){
        Log.i("SelectedIngredients", mSelectedIngredients.toString())
        if(mSelectedIngredients.isEmpty()){
            iv_check_light.visibility = View.VISIBLE
            iv_check_dark.visibility = View.GONE
        }else{
            iv_check_light.visibility = View.GONE
            iv_check_dark.visibility = View.VISIBLE
        }
    }
}
