package com.example.lassi.activities

import android.app.Dialog
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.view.WindowManager
import com.example.lassi.R
import kotlinx.android.synthetic.main.activity_edit_recipe.*
import kotlinx.android.synthetic.main.dialog_add_recipe.*

class EditRecipeActivity : AppCompatActivity() {

    private var recipeSteps: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_recipe)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val typeFaceRegular : Typeface = Typeface.createFromAsset(assets, "Quicksand-Regular.ttf")
        val typeFaceBold : Typeface = Typeface.createFromAsset(assets, "Quicksand-Bold.ttf")
        val typeFaceSemiBold : Typeface = Typeface.createFromAsset(assets, "Quicksand-SemiBold.ttf")
        tv_edit_recipe_title.typeface = typeFaceBold

        iv_add_recipe_item.setOnClickListener {
            showAddRecipeStepDialog()
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
}
