package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        creatnewActId.setOnClickListener {
            startActivity(Intent(this,CreatAccountActivity::class.java))
        }

        haveAccLoginbtnID.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }
    }
}