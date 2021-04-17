package com.example.lassi.activities

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import com.example.lassi.R
import com.example.lassi.utils.Constants
import kotlinx.android.synthetic.main.activity_ingredients_option.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_you_can_try.*

class YouCanTryActivity : AppCompatActivity() {

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
    }
}
