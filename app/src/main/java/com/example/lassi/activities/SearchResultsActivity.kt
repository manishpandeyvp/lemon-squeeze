package com.example.lassi.activities

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract
import android.util.Log
import android.view.WindowManager
import com.example.lassi.R
import com.example.lassi.models.Juice
import com.example.lassi.utils.Constants
import kotlinx.android.synthetic.main.activity_search_results.*

class SearchResultsActivity : AppCompatActivity() {

    private var mSearchedResults : ArrayList<Juice> = ArrayList()
    private var mSearchedString : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_results)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        mSearchedResults = intent.getParcelableArrayListExtra(Constants.SEARCHED_RESULTS)!!
        mSearchedString = intent.getStringExtra(Constants.SEARCHED_STRING)!!

        Log.i("SearchedResult", mSearchedResults.toString())
        Log.i("SearchedString", mSearchedString)

        val typeFaceRegular : Typeface = Typeface.createFromAsset(assets, "Quicksand-Regular.ttf")
        val typeFaceBold : Typeface = Typeface.createFromAsset(assets, "Quicksand-Bold.ttf")
        val typeFaceSemiBold : Typeface = Typeface.createFromAsset(assets, "Quicksand-SemiBold.ttf")
        val typeFaceSacramento : Typeface = Typeface.createFromAsset(assets, "Sacramento-Regular.ttf")
        tv_search_results_title.typeface = typeFaceBold
        tv_you_searched_for.typeface = typeFaceRegular

        iv_search_results_back.setOnClickListener {
            onBackPressed()
        }

        tv_you_searched_for.text = "You searched for: $mSearchedString"
    }
}
