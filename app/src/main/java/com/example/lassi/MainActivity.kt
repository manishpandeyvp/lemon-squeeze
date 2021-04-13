package com.example.lassi

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val typeFaceRegular : Typeface = Typeface.createFromAsset(assets, "Quicksand-Regular.ttf")
        val typeFaceBold : Typeface = Typeface.createFromAsset(assets, "Quicksand-Bold.ttf")
        val typeFaceSemiBold : Typeface = Typeface.createFromAsset(assets, "Quicksand-SemiBold.ttf")
        et_search.typeface = typeFaceRegular
        tv_all.typeface = typeFaceSemiBold
        tv_cold_shakes.typeface = typeFaceSemiBold
        tv_juices.typeface = typeFaceSemiBold
        tv_wishes.typeface = typeFaceBold
        tv_lorem1.typeface = typeFaceBold
        tv_lorem2.typeface = typeFaceBold
    }


}
