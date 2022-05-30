package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var refUsers: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()


        loginButtonId.setOnClickListener {
            var email = loginEmailETID.text.toString().trim()
            var password = loginPaswrdETID.text.toString().trim()
            if (email.isNotEmpty() || password.isNotEmpty()){
                loginUser(email,password)
            } else{
                Toast.makeText(this,"Please Enter Email or Password",Toast.LENGTH_LONG).show()
            }

        }


    }

    private fun loginUser(email: String, password: String) {
        mAuth!!.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                task: Task<AuthResult> ->
                if (task.isSuccessful){
                    // we have to split the display name
                        var username = email.split('@')[0]
                    var dashBoardIntent = Intent(this,DashboardActivity::class.java)
                    dashBoardIntent.putExtra("name", username)
                    startActivity(dashBoardIntent)
                    finish()
                } else {
                    Toast.makeText(this,"Login Failed...", Toast.LENGTH_LONG).show()
                }

            }

    }


}