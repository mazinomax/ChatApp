package com.example.chatapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.chatapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var refUsers: DatabaseReference
    private lateinit var mAuthListener: FirebaseAuth.AuthStateListener
    private lateinit var user: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()
//        mAuthListener = FirebaseAuth.AuthStateListener {
//            firebaseAuth: FirebaseAuth ->
//            user = firebaseAuth.currentUser!!
//            // we login to dashboard if user is already logged in
//            startActivity(Intent(this, DashboardActivity::class.java))
//            finish()
//
//        }
         mAuthListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val firebaseUser = firebaseAuth.currentUser
            if (firebaseUser != null) {
                val intent = Intent(this, DashboardActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        creatnewActId.setOnClickListener {
            startActivity(Intent(this, CreatAccountActivity::class.java))
            finish()
        }

        haveAccLoginbtnID.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
    // we login to dashboard if user is already logged in

    override fun onStart() {
        super.onStart()
//        mAuth.addAuthStateListener(mAuthListener)
        mAuth.addAuthStateListener(this.mAuthListener)


    }

    override fun onStop() {
        super.onStop()
        mAuth.removeAuthStateListener(mAuthListener)


    }
}