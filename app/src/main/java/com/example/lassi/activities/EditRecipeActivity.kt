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


    }





}
