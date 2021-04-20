package com.example.lassi.activities

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lassi.R
import com.example.lassi.adapters.SearchedResultsAdapter
import com.example.lassi.models.Juice
import com.example.lassi.utils.Constants
import kotlinx.android.synthetic.main.activity_main.*
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
        tv_search_results_title.typeface = typeFaceBold
        tv_you_searched_for.typeface = typeFaceRegular

        iv_search_results_back.setOnClickListener {
            onBackPressed()
        }

        tv_you_searched_for.text = "You searched for: $mSearchedString"

        updateJuiceAndShakesUI(mSearchedResults)
    }

    private fun updateJuiceAndShakesUI(juiceAndShakeList: ArrayList<Juice>){
        if(juiceAndShakeList.size > 0) {
            gif_nothing_found.visibility = View.GONE
            tv_nothing_found.visibility = View.GONE
            rv_search_results.layoutManager =
                LinearLayoutManager(this)
            val adapter = SearchedResultsAdapter(this, juiceAndShakeList, assets)
            rv_search_results.adapter = adapter
            adapter.setOnClickListener(object : SearchedResultsAdapter.OnClickListener {
                override fun onClick(position: Int, model: Juice) {
                    val intent = Intent(this@SearchResultsActivity, JuiceAndShakeRecipeActivity::class.java)
                    intent.putExtra(Constants.RECIPE, model)
                    startActivity(intent)
                }
            })
        }else{
            gif_nothing_found.visibility = View.VISIBLE
            tv_nothing_found.visibility = View.VISIBLE
        }
    }
}
