package com.example.innovation

import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
/*import com.google.android.material.snackbar.Snackbar*/
import androidx.appcompat.app.AppCompatActivity;
import kotlinx.android.synthetic.main.activity_main.*

import kotlinx.android.synthetic.main.activity_timer.*
import kotlinx.android.synthetic.main.content_timer.*

class timer : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val PRIVATE_MODE = 0
        val PREFERENCE_NAME: String = getString(R.string.settings_preferences)
        val PREFERENCE_VALUE: String = getString(R.string.timer_preferences)
        val shared_preference: SharedPreferences = getSharedPreferences(PREFERENCE_NAME, PRIVATE_MODE)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)
        setSupportActionBar(toolbar)
        title = "Clean n' Green"

        val timer_start: Long = shared_preference.getLong(PREFERENCE_VALUE, 300000)
        timerText3.text = "${(timer_start / 1000)/60}:${((timer_start/1000)%60)/10}${((timer_start/1000)%60)%10}"
        val counter = object : CountDownTimer(timer_start, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                timerText3.text = "${(millisUntilFinished / 1000)/60}:${((millisUntilFinished/1000)%60)/10}${((millisUntilFinished/1000)%60)%10}"
            }

            override fun onFinish() {
                timerText3.text = "Ready!"
            }
        }
        counter.start()
    }

}
