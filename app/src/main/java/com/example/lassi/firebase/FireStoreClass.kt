package com.example.lassi.firebase

import android.util.Log
import com.example.lassi.activities.MainActivity
import com.example.lassi.models.Juice
import com.example.lassi.utils.Constants
import com.google.firebase.firestore.FirebaseFirestore

class FireStoreClass {
    private val mFireStore = FirebaseFirestore.getInstance()

    fun getJuiceAnfShakesList(activity: MainActivity){
        mFireStore.collection(Constants.JUICE_AND_SHAKES).get().addOnSuccessListener {document ->
            Log.i("Juice Docs", document.documents.toString())
            val juiceAndShakesList: ArrayList<Juice> = ArrayList()
            for(i in document.documents){
                val juiceAndShake = i.toObject(Juice::class.java)!!
                juiceAndShake.id = i.id
                juiceAndShakesList.add(juiceAndShake)
            }

            activity.updateJuiceAndShakesUI(juiceAndShakesList)
        }.addOnFailureListener { e ->
            Log.e(activity.javaClass.simpleName, "Error while fetching your shakes!", e)
            activity.hideLoadingGif()
        }
    }
}