package com.example.ctrlabtest

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import java.net.HttpURLConnection
import java.net.URL


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (isInternetAvailable(context = this)){
        openWebViewActivity(httpCodeChecking(), context = this)
        }

        val button: Button = findViewById(R.id.buttonStart)
        button.setOnClickListener {
            startActivity(this, Intent(this, GameActivity::class.java), null)
        }

    }
}

//Sending request to URL to get response code
fun httpCodeChecking(): Int{

    var responseCode = 0
    val thread = Thread(Runnable {
        val url = URL("https://ohmytraff.space/api")
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        connection.connect()
        responseCode = connection.responseCode
    })
    thread.start()
    return responseCode
}

fun openWebViewActivity(responseCode: Int, context: Context){

    // 404 code not received
    if (responseCode != HttpURLConnection.HTTP_NOT_FOUND) {
        startActivity(context, Intent(context, WebViewActivity::class.java), null)
    }
}


//Checking if connection to Internet is available
fun isInternetAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            ?: false
    } else {
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo?.isConnectedOrConnecting ?: false
    }
}