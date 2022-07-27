@file:Suppress("NAME_SHADOWING")

package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_creat_account.*

class CreatAccountActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var refUsers: DatabaseReference
    private var firebaseUserID:String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_creat_account)

        mAuth = FirebaseAuth.getInstance()

        crtnewaccBtnID.setOnClickListener {
            createAccount()
        }

    }

    private fun createAccount() {
        var email = crtActEmailEtID.text.toString().trim()
        var password = crtActPaswrdEtID.text.toString().trim()
        var displayName = crtActdisplayName.text.toString().trim()

        if (displayName == ""){
            Toast.makeText(this,"Please Enter Username",Toast.LENGTH_LONG).show()
        } else if (email == ""){
            Toast.makeText(this,"Please Enter Email",Toast.LENGTH_LONG).show()
        }else if (password == ""){
            Toast.makeText(this,"Please Enter Password",Toast.LENGTH_LONG).show()

        } else {
            mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener{
                        task ->
                    if (task.isSuccessful){

                        firebaseUserID = mAuth.currentUser!!.uid
                        refUsers = FirebaseDatabase.getInstance("https://chat-app-3e533-default-rtdb.firebaseio.com/").reference.child("users").child(firebaseUserID)
                        val userHasmap = HashMap<String,Any>()
                        userHasmap["uid"] = firebaseUserID
                        userHasmap["display_name"] = displayName
                        userHasmap["status"] = "Hello There..."
                        userHasmap["thumb_image"] = "default"
                        userHasmap["image"] = "default"
                        Log.d("CHECK","TSUCCESS")
                        refUsers.updateChildren(userHasmap).addOnCompleteListener {
                            task ->
                            if (task.isSuccessful){
                                var dashBoardIntent = Intent(this,DashboardActivity::class.java)
                                dashBoardIntent.putExtra("name", displayName)
                                startActivity(dashBoardIntent)
                                finish()
                            }else {
                                Toast.makeText(this,"User Not Created",Toast.LENGTH_LONG).show()
                            }
                        }
                    } else {
                        Toast.makeText(this,"Error: " + task.exception.toString(),Toast.LENGTH_LONG).show()
                    }

                }
        }

    }

}

