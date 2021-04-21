package com.example.lassi.activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.lassi.R
import com.example.lassi.utils.Constants
import kotlinx.android.synthetic.main.activity_post_your_recipe.*
import kotlinx.android.synthetic.main.item_juice_card.*
import java.io.IOException

class PostYourRecipeActivity : AppCompatActivity() {

    private var mSelectedImageFileUri : Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_your_recipe)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val typeFaceRegular : Typeface = Typeface.createFromAsset(assets, "Quicksand-Regular.ttf")
        val typeFaceBold : Typeface = Typeface.createFromAsset(assets, "Quicksand-Bold.ttf")
        val typeFaceSemiBold : Typeface = Typeface.createFromAsset(assets, "Quicksand-SemiBold.ttf")
        val typeFaceSacramento : Typeface = Typeface.createFromAsset(assets, "Sacramento-Regular.ttf")

        iv_juice_image_post.setOnClickListener {
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                Constants.showImageChooser(this)
            }else{
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), Constants.READ_STORAGE_PERMISSION_CODE)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == Constants.READ_STORAGE_PERMISSION_CODE){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Constants.showImageChooser(this)
            } else {
                Toast.makeText(this, "Oops, you just denied the permission for storage!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == Constants.PICK_IMAGE_REQUEST_CODE && data!!.data != null){
            mSelectedImageFileUri = data.data
            try {
                Glide
                    .with(this)
                    .load(mSelectedImageFileUri).centerCrop()
                    .placeholder(R.drawable.image_placeholder)
                    .into(iv_juice_image_post)
                iv_juice_image_post.scaleType = ImageView.ScaleType.CENTER_CROP
            }catch (e: IOException){
                e.printStackTrace()
            }

        }
    }
}
