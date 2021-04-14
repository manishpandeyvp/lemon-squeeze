package com.example.lassi.activities

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.example.lassi.R
import kotlinx.android.synthetic.main.activity_juice_and_shake_recipe.*

class JuiceAndShakeRecipeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_juice_and_shake_recipe)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val typeFaceRegular : Typeface = Typeface.createFromAsset(assets, "Quicksand-Regular.ttf")
        val typeFaceBold : Typeface = Typeface.createFromAsset(assets, "Quicksand-Bold.ttf")
        val typeFaceSemiBold : Typeface = Typeface.createFromAsset(assets, "Quicksand-SemiBold.ttf")
        val typeFaceSacramento : Typeface = Typeface.createFromAsset(assets, "Sacramento-Regular.ttf")
        tv_1.typeface = typeFaceBold
        tv_2.typeface = typeFaceBold
        tv_3.typeface = typeFaceRegular
        tv_4.typeface = typeFaceSemiBold
        tv_8.typeface = typeFaceSemiBold
        tv_9.typeface = typeFaceRegular
    }
}
