package com.room.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.room.R
import com.room.config.Loading
import com.room.controller.WebController
import com.room.model.User
import okhttp3.FormBody
import okhttp3.Request
import java.net.PasswordAuthentication

class LoginActivity : AppCompatActivity() {
  private lateinit var username: EditText
  private lateinit var password: EditText
  private lateinit var login: Button
  private lateinit var user:User
  private lateinit var loading:Loading

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)

    user = User(this)
    loading = Loading(this)
    username = findViewById(R.id.editTextUsername)
    password = findViewById(R.id.editTextPassword)
    login = findViewById(R.id.buttonLogin)

    login.setOnClickListener {
      if (username.text.isEmpty() || password.text.isEmpty()) {
        //Username and Password is empty
        Toast.makeText(this, "Username or password is empty", Toast.LENGTH_LONG).show()
      } else {
        onLogin()
      }
    }

  }

  private fun onLogin() {
    val body = FormBody.Builder()
    body.addEncoded("username", "${username.text}")
    body.addEncoded("password", "${password.text}")
    val response = WebController.Post("login", body).call()


    Log.i("response", response.toString())
  }
}