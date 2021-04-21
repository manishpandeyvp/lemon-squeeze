package com.example.lassi.activities

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lassi.R
import com.example.lassi.adapters.SavedLikedItemsAdapter
import com.example.lassi.firebase.FireStoreClass
import com.example.lassi.models.Juice
import com.example.lassi.utils.Constants
import kotlinx.android.synthetic.main.activity_saved_juices.*

class SavedJuicesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_juices)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val typeFaceBold : Typeface = Typeface.createFromAsset(assets, "Quicksand-Bold.ttf")
        val typeFaceSemiBold : Typeface = Typeface.createFromAsset(assets, "Quicksand-SemiBold.ttf")

        tv_saved_title.typeface = typeFaceBold
        tv_nothing_found.typeface = typeFaceSemiBold

        iv_saved_back.setOnClickListener {
            onBackPressed()
            finish()
        }

        getSavedJuicesList()
    }

    private fun updateJuiceAndShakesUI(juiceAndShakeList: ArrayList<Juice>){
        if(juiceAndShakeList.size > 0) {
            gif_nothing_found.visibility = View.GONE
            tv_nothing_found.visibility = View.GONE
            rv_saved.layoutManager =
                LinearLayoutManager(this)
            val adapter = SavedLikedItemsAdapter(this, juiceAndShakeList, assets)
            rv_saved.adapter = adapter
            adapter.setOnClickListener(object : SavedLikedItemsAdapter.OnClickListener {
                override fun onClick(position: Int, model: Juice) {
                    val intent = Intent(this@SavedJuicesActivity, JuiceAndShakeRecipeActivity::class.java)
                    intent.putExtra(Constants.RECIPE, model)
                    startActivity(intent)
                }
            })

        }else{
            rv_saved.visibility = View.GONE
            gif_nothing_found.visibility = View.VISIBLE
            tv_nothing_found.visibility = View.VISIBLE
        }
    }

    private fun getSavedJuicesList(){
        FireStoreClass().getSavedLikedJuices(this)
    }

    fun getSavedJuicesListSuccess(mSavedJuices: ArrayList<Juice>){
        updateJuiceAndShakesUI(mSavedJuices)
    }

    override fun onStart() {
        super.onStart()
        if(FireStoreClass().getCurrentUserId().isEmpty()){
            startActivity(Intent(this@SavedJuicesActivity, OptionsDrawerActivity::class.java))
            finish()
        }else{
            if(Constants.user_data.savedList.size>0){
                getSavedJuicesList()
            }else{
                updateJuiceAndShakesUI(ArrayList())
            }
        }
    }
}
