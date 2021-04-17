package com.example.lassi.activities

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lassi.R
import com.example.lassi.adapters.IngredientOptionsListAdapter
import com.example.lassi.models.Juice
import com.example.lassi.utils.Constants
import com.google.android.material.snackbar.Snackbar
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

        val typeFaceRegular : Typeface = Typeface.createFromAsset(assets, "Quicksand-Regular.ttf")
        val typeFaceBold : Typeface = Typeface.createFromAsset(assets, "Quicksand-Bold.ttf")
        tv_ingredients_option_title.typeface = typeFaceBold
        tv_check_your_fridge.typeface = typeFaceRegular

        mJuiceAndShakesList = intent.getParcelableArrayListExtra(Constants.JUICE_AND_SHAKES_LIST)!!

        getIngredientsList()

        checkProceedingAvailability()

        iv_check_light.setOnClickListener {
            val snackBar = Snackbar.make(it, "Please select ingredients", Snackbar.LENGTH_SHORT)
            snackBar.setBackgroundTint(Color.parseColor("#206a5d"))
            snackBar.setActionTextColor(Color.parseColor("#d5ecc2"))
            snackBar.show()
        }

        iv_check_dark.setOnClickListener {
            val intent = Intent(this, YouCanTryActivity::class.java)
            intent.putExtra(Constants.SELECTED_INGREDIENTS_OPTIONS, mSelectedIngredients)
            startActivity(intent)
        }

        iv_ingredients_option_back.setOnClickListener {
            onBackPressed()
        }
    }

    private fun getIngredientsList(){
        for (i in mJuiceAndShakesList){
            for (j in i.ingredients){
                if (!mIngredients.contains(j)){
                    mIngredients.add(j)
                }
            }
        }
        updateIngredientsOptionsList()
    }

    private fun updateIngredientsOptionsList(){
        rv_ingredients_option.layoutManager = LinearLayoutManager(this)
        val ingredientsAdapter = IngredientOptionsListAdapter(this, mIngredients, assets)
        rv_ingredients_option.setHasFixedSize(true)
        rv_ingredients_option.adapter = ingredientsAdapter

        ingredientsAdapter.setOnClickListener(object :
            IngredientOptionsListAdapter.OnClickListener {
            override fun onClick(position: Int, ingredient: String, selectedList: ArrayList<String>) {
                mSelectedIngredients = selectedList
                checkProceedingAvailability()
            }
        })
    }

    private fun checkProceedingAvailability(){
        if(mSelectedIngredients.isEmpty()){
            iv_check_light.visibility = View.VISIBLE
            iv_check_dark.visibility = View.GONE
        }else{
            iv_check_light.visibility = View.GONE
            iv_check_dark.visibility = View.VISIBLE
        }
    }
}
