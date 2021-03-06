package com.example.lassi.firebase

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.example.lassi.activities.*
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

    fun getSavedLikedJuices(activity: Activity){
        mFireStore.collection(Constants.JUICE_AND_SHAKES).get().addOnSuccessListener {document ->
            val savedJuiceAndShakesList: ArrayList<Juice> = ArrayList()
            val likedJuiceAndShakesList: ArrayList<Juice> = ArrayList()
            for(i in document.documents){
                if(Constants.user_data.savedList.contains(i.id)){
                    val juiceAndShake = i.toObject(Juice::class.java)!!
                    juiceAndShake.id = i.id
                    savedJuiceAndShakesList.add(juiceAndShake)
                }
                if(Constants.user_data.likedJuices.contains(i.id)){
                    val juiceAndShake = i.toObject(Juice::class.java)!!
                    juiceAndShake.id = i.id
                    likedJuiceAndShakesList.add(juiceAndShake)
                }
            }

            if(activity is SavedJuicesActivity){
                activity.getSavedJuicesListSuccess(savedJuiceAndShakesList)
            }
            if(activity is LikedJuicesActivity){
                activity.getLikedJuicesListSuccess(likedJuiceAndShakesList)
            }


        }.addOnFailureListener { e ->
            Log.e(activity.javaClass.simpleName, "Error while fetching your shakes!", e)
        }
    }

    fun registerUser(activity: Activity){
        val userHashMap = HashMap<String, Any>()
        userHashMap["userId"] = getCurrentUserId()

        mFireStore.collection(Constants.USERS).document(getCurrentUserId()).set(userHashMap, SetOptions.merge()).addOnSuccessListener {
            if(activity is OptionsDrawerActivity){
                activity.userRegistrationSuccess()
            }
        }.addOnFailureListener { e ->
            Log.e(activity.javaClass.simpleName, "Error while registering user", e)
        }
    }

    fun getUserData(activity: Activity){
        mFireStore.collection(Constants.USERS).document(getCurrentUserId()).get().addOnSuccessListener { document ->
            val userData = document.toObject(User::class.java)!!
            if(activity is OptionsDrawerActivity){
                activity.updateUserData(userData)
            }
            if(activity is MainActivity){
                activity.updateUserData(userData)
            }
        }.addOnFailureListener { e ->
            Log.e(activity.javaClass.simpleName, "Error while fetching User Data", e)
        }
    }

    fun updateUserData(activity: Activity){
        val updatedUserData: User = Constants.user_data
        mFireStore.collection(Constants.USERS).document(getCurrentUserId()).set(updatedUserData, SetOptions.merge()).addOnSuccessListener {
            if(activity is JuiceAndShakeRecipeActivity){
                activity.updateUserDataSuccess()
            }
        }.addOnFailureListener { e ->
            Log.e(activity.javaClass.simpleName, "Error while updating user", e)
        }
    }

    fun postRecipe(activity: PostYourRecipeActivity, mJuice: Juice){
        mFireStore.collection(Constants.JUICE_AND_SHAKES).document().set(mJuice, SetOptions.merge()).addOnSuccessListener {
            Log.e(activity.javaClass.simpleName, "Recipe Posted Successfully")
            Toast.makeText(activity, "Recipe Posted Successfully!!", Toast.LENGTH_SHORT).show()
            activity.recipePostedSuccessfully()
        }.addOnFailureListener { exception ->
            activity.hideProgressDialog()
            Log.e(activity.javaClass.simpleName, "Error while posting recipe", exception)
        }
    }

    fun getCurrentUserId(): String{
        val currentUser = FirebaseAuth.getInstance().currentUser
        var currentUserID = ""
        if(currentUser != null){
            currentUserID = currentUser.uid
        }
        return currentUserID
    }
}