package com.example.lassi.activities

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.example.lassi.R
import com.example.lassi.firebase.FireStoreClass
import com.example.lassi.models.User
import com.example.lassi.utils.Constants
import com.example.lassi.utils.SavedPreference
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_options_drawer.*

class OptionsDrawerActivity : AppCompatActivity() {

    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private val firebaseAuth= FirebaseAuth.getInstance()
    private var mUserData : User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_options_drawer)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val typeFaceSemiBold : Typeface = Typeface.createFromAsset(assets, "Quicksand-SemiBold.ttf")
        val typeFaceSacramento : Typeface = Typeface.createFromAsset(assets, "Sacramento-Regular.ttf")
        tv_drawer_title.typeface = typeFaceSacramento
        tv_saved_recipe.typeface = typeFaceSemiBold
        tv_liked_recipe.typeface = typeFaceSemiBold
        tv_try_and_post.typeface = typeFaceSacramento
        tv_sign_in.typeface = typeFaceSemiBold
        tv_sign_out.typeface = typeFaceSemiBold

        iv_recipe_back.setOnClickListener {
            onBackPressed()
        }

        // Google Sign in

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient= GoogleSignIn.getClient(this,gso)

        sign_in_with_google.setOnClickListener {
            signInGoogle()
        }

        sign_out.setOnClickListener {
            mGoogleSignInClient.signOut().addOnCompleteListener {
                sign_in_with_google.visibility = View.VISIBLE
                sign_out.visibility = View.GONE
            }
            Constants.user_data = User()
        }
    }

    private  fun signInGoogle(){
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent,Constants.GOOGLE_SIGN_IN_REQ_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==Constants.GOOGLE_SIGN_IN_REQ_CODE){
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleResult(task)
        }
    }

    private fun handleResult(completedTask: Task<GoogleSignInAccount>){
        try {
            val account: GoogleSignInAccount? = completedTask.getResult(ApiException::class.java)
            if (account != null) {
                updateUI(account)
            }
        } catch (e:ApiException){
            Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(account: GoogleSignInAccount){
        val credential= GoogleAuthProvider.getCredential(account.idToken,null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {task->
            if(task.isSuccessful) {
                SavedPreference.setEmail(this,account.email.toString())
                SavedPreference.setUsername(this,account.displayName.toString())
                sign_in_with_google.visibility = View.GONE
                sign_out.visibility = View.VISIBLE
                getUserData()
            }
        }
    }

    private fun getUserData(){
        FireStoreClass().registerUser(this)
    }

    fun userRegistrationSuccess(){
        FireStoreClass().getUserData(this)
    }

    fun updateUserData(mUserData: User){
        this.mUserData = mUserData
    }

    override fun onStart() {
        super.onStart()
        if(GoogleSignIn.getLastSignedInAccount(this)!=null){
            sign_in_with_google.visibility = View.GONE
            sign_out.visibility = View.VISIBLE
            getUserData()
        }
    }
}
