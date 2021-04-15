package com.example.lassi.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import com.example.lassi.R
import com.example.lassi.models.Juice
import com.example.lassi.utils.Constants

class IngredientsOption : AppCompatActivity() {

    private lateinit var mJuiceAndShakesList: ArrayList<Juice>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingredients_option)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        mJuiceAndShakesList = intent.getParcelableArrayListExtra(Constants.JUICE_AND_SHAKES_LIST)!!
        Log.i("mJuiceAndShakesList", mJuiceAndShakesList.toString())
    }
}
