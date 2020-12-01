package com.room.model

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.text.BoringLayout

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

  fun setInteger(id: String, value: Int){
    sharedPreferencesEditor.putInt(id,value)
    sharedPreferencesEditor.commit()
  }

  fun setBoolean(id: String, value: Boolean){
    sharedPreferencesEditor.putBoolean(id,value)
    sharedPreferencesEditor.commit()
  }

  fun setString(id: String, value: String){
    sharedPreferencesEditor.putString(id,value)
    sharedPreferencesEditor.commit()
  }

  fun setLong(id: String, value: Long){
    sharedPreferencesEditor.putLong(id,value)
    sharedPreferencesEditor.commit()
  }

  fun getInteger(id: String, value: Int): Int{
    return sharedPreferences.getInt(id,0)
  }

  fun getBoolean(id: String, value: Boolean): Boolean{
    return sharedPreferences.getBoolean(id,false)
  }

  fun getString(id: String, value: String): String{
    return sharedPreferences.getString(id,"")!!
  }

  fun getLong(id: String, value: Long): Long{
    return sharedPreferences.getLong(id,0)
  }

  fun clear(){
    sharedPreferences.edit().clear().apply()
    sharedPreferencesEditor.clear()
  }

}