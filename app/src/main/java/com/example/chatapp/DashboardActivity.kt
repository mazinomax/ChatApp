package com.example.chatapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

class DashboardActivity : AppCompatActivity() {
   var mAuth: FirebaseAuth? = null
    var mDatabase: DatabaseReference? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        mAuth = FirebaseAuth.getInstance()


    }
}