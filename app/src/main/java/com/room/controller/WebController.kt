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

    class Post(
        private var targetUrl: String,
        private var token: String,
        private var bodyValue: FormBody.Builder
    ) : Callable<JSONObject> {
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
                val input = BufferedReader(InputStreamReader(response.body!!.byteStream()))
                val inputData: String = input.readLine()
                val convertJSON = JSONObject(inputData)
                return when {
                    response.isSuccessful -> {
                        JSONObject().put("code", 200).put("data", convertJSON)
                    }
                    else -> {
                        when {
                            convertJSON.toString().contains("errors") -> {
                                JSONObject().put("code", 500).put(
                                    "data", convertJSON.getJSONObject("errors")
                                        .getJSONArray(
                                            convertJSON.getJSONObject("errors")
                                                .names()[0].toString()
                                        )[0]
                                )
                            }
                            convertJSON.toString().contains("message") -> {
                                JSONObject().put("code", 500)
                                    .put("data", convertJSON.getString("meassage"))
                            }
                            else -> {
                                JSONObject().put("code", 500).put("data", convertJSON)
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                JSONObject().put("code", 500).put("data", e.message)
            }
        }

    }

    class Get(
        private var targetUrl: String,
        private var token: String,
        private var bodyValue: FormBody.Builder?
    ) : Callable<JSONObject> {
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
                val input = BufferedReader(InputStreamReader(response.body!!.byteStream()))
                val inputData: String = input.readLine()
                val convertJSON = JSONObject(inputData)
                return when {
                    response.isSuccessful -> {
                        JSONObject().put("code", 200).put("data", convertJSON)
                    }
                    else -> {
                        when {
                            convertJSON.toString().contains("errors") -> {
                                JSONObject().put("code", 500).put(
                                    "data", convertJSON.getJSONObject("errors")
                                        .getJSONArray(
                                            convertJSON.getJSONObject("errors")
                                                .names()[0].toString()
                                        )[0]
                                )
                            }
                            convertJSON.toString().contains("message") -> {
                                JSONObject().put("code", 500)
                                    .put("data", convertJSON.getString("meassage"))
                            }
                            else -> {
                                JSONObject().put("code", 500).put("data", convertJSON)
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                JSONObject().put("code", 500).put("data", e.message)
            }
            

        }
    }

}

