package com.example.lassi.firebase

import android.app.Activity
import android.util.Log
import com.example.lassi.activities.MainActivity
import com.example.lassi.activities.YouCanTryActivity
import com.example.lassi.models.Juice
import com.example.lassi.utils.Constants
import com.google.firebase.firestore.FirebaseFirestore

class FireStoreClass {
    private val mFireStore = FirebaseFirestore.getInstance()

    fun getJuiceAnfShakesList(activity: Activity){
        mFireStore.collection(Constants.JUICE_AND_SHAKES).get().addOnSuccessListener {document ->
            Log.i("Juice Docs", document.documents.toString())
            val juiceAndShakesList: ArrayList<Juice> = ArrayList()
            for(i in document.documents){
                val juiceAndShake = i.toObject(Juice::class.java)!!
                juiceAndShake.id = i.id
                juiceAndShakesList.add(juiceAndShake)
            }

            if(activity is MainActivity){
                activity.updateJuiceAndShakesUI(juiceAndShakesList)
            }
            if(activity is YouCanTryActivity){
                activity.updateJuiceAndShakesUI(juiceAndShakesList)
            }

        }.addOnFailureListener { e ->
            Log.e(activity.javaClass.simpleName, "Error while fetching your shakes!", e)
            if(activity is MainActivity){
                activity.hideLoadingGif()
            }
            if(activity is YouCanTryActivity){
                activity.hideLoadingGif()
            }

        }
    }
}