package com.example.ledapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.io.StringReader
import android.widget.Button
import com.beust.klaxon.Klaxon
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException
import okhttp3.RequestBody.Companion.toRequestBody
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button1: Button = findViewById(R.id.LED_ON)
        val button0: Button = findViewById(R.id.LED_OFF)
        val buttonr: Button = findViewById(R.id.Refresh)
        button1.setOnClickListener {
            funButton0()
        }
        button0.setOnClickListener {
            funButton2()
        }
        buttonr.setOnClickListener{
            funButton1()
        }
    }
    private fun funButton1() {
        println("Attempting to get JSON data!")
        val url = "https://api.thingspeak.com/channels/1029606/feeds.json?results=1"

        val request = Request.Builder().url(url).build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                println(body) // This is the json body being created

                val gson = GsonBuilder().create()
                val json = gson.fromJson(body, Json::class.java)
                val bruh = (((json as Json).feeds as java.util.ArrayList<*>)[0] as Feed).field1 // THis section of the code breaks down the body and only give field 1
                val strbruh = bruh.toString()
                textView.text = strbruh









            }
            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute request!")
            }

        })


    }


    private fun funButton0() {
        val payload = "test payload"

        val okHttpClient = OkHttpClient()
        val requestBody = payload.toRequestBody()
        val request = Request.Builder()
            .method("POST", requestBody)
            .url("https://api.thingspeak.com/update?api_key=G2VRWM1MCXFJMRVD&field1=1")
            .build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // Handle this
            }

            override fun onResponse(call: Call, response: Response) {
                // Handle this
            }
        })

    }
    private fun funButton2(){
        val payload = "test payload"

        val okHttpClient = OkHttpClient()
        val requestBody = payload.toRequestBody()
        val request = Request.Builder()
            .method("POST", requestBody)
            .url("https://api.thingspeak.com/update?api_key=G2VRWM1MCXFJMRVD&field1=0")
            .build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // Handle this
            }

            override fun onResponse(call: Call, response: Response) {
                // Handle this
            }
        })
    }
}


class Json(val feeds: List<Feed>)
class Feed(val field1: Int)

