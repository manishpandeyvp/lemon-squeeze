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

        val typeFace : Typeface = Typeface.createFromAsset(assets, "Quicksand-VariableFont_wght.ttf")
        et_search.typeface = typeFace
        tv_all.typeface = typeFace
        tv_cold_shakes.typeface = typeFace
        tv_juices.typeface = typeFace
        tv_wishes.typeface = typeFace
    }


}
