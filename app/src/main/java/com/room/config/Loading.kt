package com.room.config

import android.R.style.Theme_Translucent_NoTitleBar
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import com.room.R

@SuppressLint("InflateParams")
class Loading(private val activity: Activity) {
  private val dialog = Dialog(activity, Theme_Translucent_NoTitleBar)

  init {
    val view = activity.layoutInflater.inflate(R.layout.activity_main, null)
    dialog.setContentView(view)
    dialog.setCancelable(false)
  }

  fun openDialog() {
    dialog.show()
  }

  fun closeDialog() {
    dialog.dismiss()
  }
}