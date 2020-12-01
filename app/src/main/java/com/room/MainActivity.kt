package com.room

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.room.view.HomeActivity
import com.room.view.LoginActivity
import java.util.*
import kotlin.concurrent.schedule

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    Timer().schedule(2000){
      runOnUiThread{
        val intent = Intent(applicationContext, LoginActivity::class.java)
        startActivity(intent)
      }
    }

  }
}