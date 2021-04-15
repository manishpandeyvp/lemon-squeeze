package com.example.lassi.activities

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.lassi.R
import com.example.lassi.adapters.IngredientsListAdapter
import com.example.lassi.adapters.RecipeAdapter
import com.example.lassi.models.Juice
import com.example.lassi.utils.Constants
import kotlinx.android.synthetic.main.activity_juice_and_shake_recipe.*
import kotlinx.android.synthetic.main.item_juice_card.view.*

class JuiceAndShakeRecipeActivity : AppCompatActivity() {

    private lateinit var mRecipe: Juice

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_juice_and_shake_recipe)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        mRecipe = intent.getParcelableExtra(Constants.RECIPE)!!
        Log.i("JuiceShake mRecipe", mRecipe.toString())

        val typeFaceRegular : Typeface = Typeface.createFromAsset(assets, "Quicksand-Regular.ttf")
        val typeFaceBold : Typeface = Typeface.createFromAsset(assets, "Quicksand-Bold.ttf")
        val typeFaceSemiBold : Typeface = Typeface.createFromAsset(assets, "Quicksand-SemiBold.ttf")
        val typeFaceSacramento : Typeface = Typeface.createFromAsset(assets, "Sacramento-Regular.ttf")
        tv_1.typeface = typeFaceBold
        tv_recipe_title.typeface = typeFaceBold
        tv_recipe_desc.typeface = typeFaceRegular
        tv_4.typeface = typeFaceSemiBold
        tv_8.typeface = typeFaceSemiBold

        updateUI(mRecipe, this)

        iv_recipe_back.setOnClickListener {
            onBackPressed()
        }
    }

    private fun updateUI(mJuice: Juice, context: Context){
        rv_ingredients.layoutManager = LinearLayoutManager(this)
        val ingredientsAdapter = IngredientsListAdapter(this, mJuice.ingredients, assets)
        rv_ingredients.adapter = ingredientsAdapter

        rv_recipe.layoutManager = LinearLayoutManager(this)
        val recipeAdapter = RecipeAdapter(this, mJuice.recipe, assets)
        rv_recipe.adapter = recipeAdapter

        Glide.with(context)
            .load(mJuice.image)
            .centerCrop()
            .placeholder(R.drawable.image_placeholder)
            .into(iv_recipe_image)

        tv_recipe_title.text = mJuice.title

        tv_recipe_desc.text = mJuice.desc
    }

}
