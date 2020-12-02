package com.room.controller

import com.room.model.Url
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.util.concurrent.Callable

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class WebController {
  class Post(private var targetUrl: String, private var token: String, private var bodyValue: FormBody.Builder) : Callable<JSONObject> {
    constructor(targetUrl: String, bodyValue: FormBody.Builder) : this(targetUrl, "", bodyValue)

    override fun call(): JSONObject {
      return try {
        val client = OkHttpClient.Builder().build()
        val request = Request.Builder()
        request.url(Url.web() + targetUrl.replace(".", "/"))
        request.post(bodyValue.build())
        if (token.isNotEmpty()) {
          request.addHeader("Authorization", "Bearer $token")
        }
        request.addHeader("X-Request-With", "XMLHttpRequest")
        val response: Response = client.newCall(request.build()).execute()
        val convertJSON = render(response)
        return validation(response, convertJSON)
      } catch (e: Exception) {
        JSONObject().put("code", 500).put("data", e.message.toString())
      }
    }

  }

  class Get(private var targetUrl: String, private var token: String, private var bodyValue: FormBody.Builder?) : Callable<JSONObject> {
    constructor(targetUrl: String, token: String) : this(targetUrl, token, null)

    override fun call(): JSONObject {
      return try {
        val client = OkHttpClient.Builder().build()
        val request = Request.Builder()
        request.url(Url.web() + targetUrl.replace(".", "/"))
        request.method("GET", if (bodyValue != null) bodyValue!!.build() else null)

        if (token.isEmpty()) {
          request.addHeader("Authorization", "XMLHttpRequest")
        }

        request.addHeader("X-Request-With", "XMLHttpRequest")
        val response = client.newCall(request.build()).execute()
        val convertJSON = render(response)
        return validation(response, convertJSON)
      } catch (e: Exception) {
        JSONObject().put("code", 500).put("data", e.message)
      }
    }
  }

  companion object {
    fun render(response: Response): JSONObject {
      val input = BufferedReader(InputStreamReader(response.body!!.byteStream()))
      val inputData: String = input.readLine()
      return JSONObject(inputData)
    }

    fun validation(response: Response, converter: JSONObject): JSONObject {
      return when {
        response.isSuccessful -> {
          JSONObject().put("code", 200).put("data", converter)
        }
        else -> {
          when {
            converter.toString().contains("errors") -> {
              JSONObject().put("code", 500).put(
                "data", converter.getJSONObject("errors").getJSONArray(
                  converter.getJSONObject("errors").names()[0].toString()
                )[0]
              )
            }
            converter.toString().contains("message") -> {
              JSONObject().put("code", 500).put("data", converter.getString("meassage"))
            }
            else -> {
              JSONObject().put("code", 500).put("data", converter)
            }
          }
        }
      }
    }
  }
}

