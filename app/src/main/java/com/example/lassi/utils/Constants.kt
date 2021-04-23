package com.example.lassi.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.webkit.MimeTypeMap
import com.example.lassi.models.User

object Constants {
    const val JUICE_AND_SHAKES = "juiceAndShakes"
    const val RECIPE = "recipe"
    const val JUICE_AND_SHAKES_LIST = "juiceAndShakesList"
    const val SELECTED_INGREDIENTS_OPTIONS = "selectedIngredientsOption"
    const val SEARCHED_RESULTS = "searchedResults"
    const val SEARCHED_STRING = "searchedString"
    const val GOOGLE_SIGN_IN_REQ_CODE : Int = 123
    const val USERS = "users"
    var user_data: User = User()
    const val RECIPE_STEPS = ""
    const val EDIT_RECIPE_STEPS = ""

    const val READ_STORAGE_PERMISSION_CODE = 1
    const val PICK_IMAGE_REQUEST_CODE = 2

    fun showImageChooser(activity: Activity){
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        activity.startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST_CODE)
    }

    fun getFileExtension(activity: Activity, uri: Uri?): String?{
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(activity.contentResolver.getType(uri!!))
    }
}