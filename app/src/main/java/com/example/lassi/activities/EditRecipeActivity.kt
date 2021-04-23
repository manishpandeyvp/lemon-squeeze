package com.example.lassi.activities

import android.app.Dialog
import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lassi.R
import com.example.lassi.adapters.AddRecipeItemsListAdapter
import com.example.lassi.utils.Constants
import kotlinx.android.synthetic.main.activity_edit_recipe.*
import kotlinx.android.synthetic.main.dialog_add_recipe.*
import kotlin.collections.ArrayList

class EditRecipeActivity : AppCompatActivity() {

    private var recipeSteps: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_recipe)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val typeFaceBold : Typeface = Typeface.createFromAsset(assets, "Quicksand-Bold.ttf")
        tv_edit_recipe_title.typeface = typeFaceBold

        iv_add_recipe_item.setOnClickListener {
            showAddRecipeStepDialog()
        }

        recipeSteps = Constants.POST_RECIPE.recipe
        updateRecipeListUI(recipeSteps)

        iv_done_editing_recipe.setOnClickListener {
            val intent = Intent(this, PostYourRecipeActivity::class.java)
            Constants.POST_RECIPE.recipe = recipeSteps
            Log.i("RECIPE_LIST_EDIT", Constants.POST_RECIPE.recipe.toString())
            startActivity(intent)
            finish()
        }


    }

    private fun showAddRecipeStepDialog(){
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_add_recipe)
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        val typeFaceRegular : Typeface = Typeface.createFromAsset(assets, "Quicksand-Regular.ttf")
        val typeFaceSemiBold : Typeface = Typeface.createFromAsset(assets, "Quicksand-SemiBold.ttf")
        dialog.tv_add_recipe_step_head.typeface = typeFaceSemiBold
        dialog.et_recipe_step.typeface = typeFaceRegular
        dialog.tv_ok_add_recipe.typeface = typeFaceSemiBold
        dialog.tv_cancel_add_recipe.typeface = typeFaceRegular

        dialog.setCanceledOnTouchOutside(false)

        dialog.tv_ok_add_recipe.setOnClickListener {
            if(dialog.et_recipe_step.text.toString().isNotEmpty()){
                recipeSteps.add(dialog.et_recipe_step.text.toString())
                updateRecipeListUI(recipeSteps)
                dialog.dismiss()
            }else{
                dialog.dismiss()
            }
        }

        dialog.tv_cancel_add_recipe.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun updateRecipeListUI(mRecipeList: ArrayList<String>){
        rv_edit_recipe.layoutManager = LinearLayoutManager(this)
        val adapter = AddRecipeItemsListAdapter(this, mRecipeList, assets)
        rv_edit_recipe.setHasFixedSize(true)
        rv_edit_recipe.adapter = adapter

        adapter.setOnClickListener(object : AddRecipeItemsListAdapter.OnClickListener{
            override fun onClick(position: Int, ingredient: String) {
                recipeSteps.remove(ingredient)
                updateRecipeListUI(recipeSteps)
            }
        })
    }

}
