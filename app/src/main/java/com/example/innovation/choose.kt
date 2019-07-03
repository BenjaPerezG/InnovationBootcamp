package com.example.innovation

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
/*import com.google.android.material.snackbar.Snackbar*/
import androidx.appcompat.app.AppCompatActivity;

import kotlinx.android.synthetic.main.activity_choose.*
import kotlinx.android.synthetic.main.content_choose.*

class choose : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val PRIVATE_MODE = 0
        val PREFERENCE_NAME: String = getString(R.string.settings_preferences)
        val PREFERENCE_VALUE: String = getString(R.string.timer_preferences)
        val shared_preference: SharedPreferences = getSharedPreferences(PREFERENCE_NAME, PRIVATE_MODE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose)
        setSupportActionBar(toolbar)
        val timerIntent = Intent(applicationContext, timer::class.java)

        button2.setOnClickListener {
            shared_preference.edit().putLong(PREFERENCE_VALUE, 180000).commit()
            startActivity(timerIntent)
        }
        button3.setOnClickListener {
            shared_preference.edit().putLong(PREFERENCE_VALUE, 300000).commit()
            startActivity(timerIntent)
        }
        button4.setOnClickListener {
            shared_preference.edit().putLong(PREFERENCE_VALUE, 600000).commit()
            startActivity(timerIntent)
        }
        button5.setOnClickListener {
            shared_preference.edit().putLong(PREFERENCE_VALUE, 900000).commit()
            startActivity(timerIntent)
        }

        /*fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }*/
    }

}
