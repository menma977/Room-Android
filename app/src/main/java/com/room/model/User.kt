package com.room.model

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

@SuppressLint("CommitPrefEdits")
class User(context: Context) {
  private val sharedPreferences: SharedPreferences
  private val sharedPreferencesEditor: SharedPreferences.Editor

  init {
    sharedPreferences = context.getSharedPreferences(userData, Context.MODE_PRIVATE)
    sharedPreferencesEditor = sharedPreferences.edit()
  }

  companion object {
    private const val userData = "user"
  }
}