package com.example.chatapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_settings.*

class settingsActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDatabase: DatabaseReference
    private lateinit var mStorageRef: StorageReference
    //private lateinit var mCurrentUser: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        mAuth = FirebaseAuth.getInstance()

       var mCurrentUser = FirebaseAuth.getInstance().currentUser!!

        var userId = mCurrentUser.uid
        mDatabase = FirebaseDatabase.getInstance().reference.child("users").child(userId)


        mDatabase.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                  var displayname = dataSnapshot.child("display_name").value
                  var userStatus = dataSnapshot.child("status").getValue()
                  var thumbImage = dataSnapshot.child("thumb_image").value

                settDisplayID.text = displayname.toString()
                settstatusTVID.text = userStatus.toString()
            }

            override fun onCancelled(databaseErrorSnapshot: DatabaseError) {
            }
        })

        settChangeStatusButnID.setOnClickListener {
            var statusintent = Intent(this,StatusActivity::class.java)
            statusintent.putExtra("status",settstatusTVID.text.toString().trim())
            startActivity(statusintent)
        }


    }

}