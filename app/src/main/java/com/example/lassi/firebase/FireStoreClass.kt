package com.example.lassi.firebase

import android.app.Activity
import android.util.Log
import com.example.lassi.activities.MainActivity
import com.example.lassi.activities.OptionsDrawerActivity
import com.example.lassi.activities.YouCanTryActivity
import com.example.lassi.models.Juice
import com.example.lassi.models.User
import com.example.lassi.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class FireStoreClass {
    private val mFireStore = FirebaseFirestore.getInstance()

    fun getJuiceAnfShakesList(activity: Activity){
        mFireStore.collection(Constants.JUICE_AND_SHAKES).get().addOnSuccessListener {document ->
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

    fun getUserData(activity: Activity){
        val user: User = User()
        user.userId = getCurrentUserId()
        mFireStore.collection(Constants.USERS).document(getCurrentUserId()).set(user, SetOptions.merge()).addOnSuccessListener { document ->
            Log.i("UpdatedUserData", document.toString())
        }.addOnFailureListener { e ->
            Log.e(activity.javaClass.simpleName, "Error while fetching User Data", e)
        }
    }

    private fun getCurrentUserId(): String{
        val currentUser = FirebaseAuth.getInstance().currentUser
        var currentUserID = ""
        if(currentUser != null){
            currentUserID = currentUser.uid
        }
        return currentUserID
    }
}