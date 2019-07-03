package com.example.innovation

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

   /* val PRIVATE_MODE = 0
    val PREFERENCE_NAME: String = getString(R.string.settings_preferences)
    val PREFERENCE_VALUE: String = getString(R.string.timer_preferences)
    val shared_preference: SharedPreferences = getSharedPreferences(PREFERENCE_NAME, PRIVATE_MODE)*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /*val timer_start: Long = shared_preference.getLong(PREFERENCE_VALUE, 30000)*/
        val counter = object : CountDownTimer(300000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                timerText.text = "seconds remaining: ${(millisUntilFinished / 1000)/60}:${((millisUntilFinished/1000)%60)/10}${((millisUntilFinished/1000)%60)%10}"
            }

            override fun onFinish() {
                timerText.text = "done!"
            }
        }
        startTimerButton.setOnClickListener { counter.start() }

    }
}
