package com.example.innovation

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private fun Connect(){}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        connectBluetoothButton.setOnClickListener {
            Connect()
            val timer_intent = Intent(applicationContext, choose::class.java)
            startActivity(timer_intent)
        }
    }
}
