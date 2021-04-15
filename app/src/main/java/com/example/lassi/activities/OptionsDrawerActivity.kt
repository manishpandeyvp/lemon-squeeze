package com.example.lassi.activities

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.example.lassi.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_options_drawer.*

class OptionsDrawerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_options_drawer)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val typeFaceRegular : Typeface = Typeface.createFromAsset(assets, "Quicksand-Regular.ttf")
        val typeFaceBold : Typeface = Typeface.createFromAsset(assets, "Quicksand-Bold.ttf")
        val typeFaceSemiBold : Typeface = Typeface.createFromAsset(assets, "Quicksand-SemiBold.ttf")
        val typeFaceSacramento : Typeface = Typeface.createFromAsset(assets, "Sacramento-Regular.ttf")
        tv_drawer_title.typeface = typeFaceSacramento
        tv_saved_recipe.typeface = typeFaceSemiBold
        tv_liked_recipe.typeface = typeFaceSemiBold
        tv_try_and_post.typeface = typeFaceSacramento
        tv_sign_in.typeface = typeFaceSemiBold
        tv_sign_out.typeface = typeFaceSemiBold

        iv_recipe_back.setOnClickListener {
            onBackPressed()
        }
    }
}
