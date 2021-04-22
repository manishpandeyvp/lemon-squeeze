package com.example.lassi.activities

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.lassi.R
import com.example.lassi.adapters.AddedIngredientsItemsListAdapter
import com.example.lassi.models.Juice
import com.example.lassi.utils.Constants
import kotlinx.android.synthetic.main.activity_post_your_recipe.*
import kotlinx.android.synthetic.main.dialog_add_ingredient.*
import kotlinx.android.synthetic.main.dialog_recipe_title_desc.*
import kotlinx.android.synthetic.main.dialog_recipe_title_desc.tv_cancel
import kotlinx.android.synthetic.main.dialog_recipe_title_desc.tv_ok
import java.io.IOException

class PostYourRecipeActivity : AppCompatActivity() {

    private var mSelectedImageFileUri : Uri? = null
    private var mJuice: Juice = Juice()

    companion object{
        const val RECIPE_ACTIVITY_REQUEST_CODE = 1
    }

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
        tv_post_your_recipe_title.typeface = typeFaceBold
        tv_recipe_title.typeface = typeFaceSemiBold
        tv_recipe_desc.typeface = typeFaceRegular
        tv_ingredient.typeface = typeFaceSemiBold
        tv_recipe.typeface = typeFaceSemiBold

        iv_juice_image_post.setOnClickListener {
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                Constants.showImageChooser(this)
            }else{
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), Constants.READ_STORAGE_PERMISSION_CODE)
            }
        }

        iv_edit_recipe_title_desc.setOnClickListener {
            showEditTitleDescDialog()
        }

        iv_add_ingredient.setOnClickListener {
            showAddIngredientDialog()
        }

        iv_edit_recipe.setOnClickListener {
            val intent = Intent(this, EditRecipeActivity::class.java)
            intent.putExtra(Constants.RECIPE_STEPS, mJuice.recipe)
            startActivityForResult(intent, RECIPE_ACTIVITY_REQUEST_CODE)
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

    private fun showEditTitleDescDialog(){
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_recipe_title_desc)
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        val typeFaceRegular : Typeface = Typeface.createFromAsset(assets, "Quicksand-Regular.ttf")
        val typeFaceSemiBold : Typeface = Typeface.createFromAsset(assets, "Quicksand-SemiBold.ttf")
        dialog.tv_edit_title_desc_head.typeface = typeFaceSemiBold
        dialog.et_edit_title.typeface = typeFaceRegular
        dialog.et_edit_desc.typeface = typeFaceRegular
        dialog.tv_ok.typeface = typeFaceSemiBold
        dialog.tv_cancel.typeface = typeFaceRegular

        val title = tv_recipe_title.text.toString()
        dialog.et_edit_title.setText(title)
        dialog.et_edit_title.setSelection(title.length)

        val desc = tv_recipe_desc.text.toString()
        dialog.et_edit_desc.setText(desc)
        dialog.et_edit_desc.setSelection(desc.length)

        dialog.setCanceledOnTouchOutside(false)

        dialog.tv_ok.setOnClickListener {
            mJuice.title = dialog.et_edit_title.text.toString()
            mJuice.desc = dialog.et_edit_desc.text.toString()
            tv_recipe_title.text = mJuice.title
            tv_recipe_desc.text = mJuice.desc
            dialog.dismiss()
        }

        dialog.tv_cancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showAddIngredientDialog(){
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_add_ingredient)
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.setCanceledOnTouchOutside(false)

        val typeFaceRegular : Typeface = Typeface.createFromAsset(assets, "Quicksand-Regular.ttf")
        val typeFaceSemiBold : Typeface = Typeface.createFromAsset(assets, "Quicksand-SemiBold.ttf")
        dialog.tv_add_ingredient_head.typeface = typeFaceSemiBold
        dialog.et_add_ingredient.typeface = typeFaceRegular
        dialog.tv_ok_add_ingredient.typeface = typeFaceSemiBold
        dialog.tv_cancel_add_ingredient.typeface = typeFaceRegular

        dialog.tv_ok_add_ingredient.setOnClickListener {
            if (dialog.et_add_ingredient.text.isNotEmpty()){
                mJuice.ingredients.add(dialog.et_add_ingredient.text.toString())
                updateIngredientListUI(mJuice.ingredients)
                dialog.dismiss()
            }else{
                dialog.dismiss()
            }
        }

        dialog.tv_cancel_add_ingredient.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun updateIngredientListUI(mIngredient: ArrayList<String>){
        rv_ingredients.layoutManager = LinearLayoutManager(this)
        val adapter = AddedIngredientsItemsListAdapter(this, mIngredient, assets)
        rv_ingredients.adapter = adapter
    }
}
