package com.kz25.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    var plays: Int = Random.nextInt(0,1000)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvPlays.text = "${plays.toString()} ${tvPlays.text.split(" ")[1]}"
    }

    fun changeUser(view: View) {
        if (btnChangeUser.text == "Apply") {
            btnChangeUser.text = "CHANGE USER"
            tvUserName.visibility = View.VISIBLE
            etUserName.visibility = View.INVISIBLE
            tvUserName.text = etUserName.text
        }else {
            btnChangeUser.text = "Apply"
            tvUserName.visibility = View.INVISIBLE
            etUserName.visibility = View.VISIBLE
        }
    }

    fun start(view: View) {
        plays++
        tvPlays.text = "${plays.toString()} ${tvPlays.text.split(" ")[1]}"
    }

    fun next(view: View) {
        Toast.makeText(this, "Skipping to next track", Toast.LENGTH_SHORT).show()
    }

    fun previous(view: View) {
        Toast.makeText(this, "Skipping to previous track", Toast.LENGTH_SHORT).show()
    }
}
