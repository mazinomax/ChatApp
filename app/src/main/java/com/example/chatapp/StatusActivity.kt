package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_status.*

class StatusActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDatabase: DatabaseReference
    private lateinit var mStorageRef: StorageReference
    private lateinit var mprogressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_status)

        mAuth = FirebaseAuth.getInstance()



//      retrieving default firebase status
        if (intent.extras != null){
            var oldStatus = intent.extras!!.get("status")
            statusUpdateEtID.setText(oldStatus.toString())
        }
        if (intent.extras?.isEmpty == true){
            statusUpdateEtID.setText("Please Enter New Status")
        }


        statusUpdateBtnID.setOnClickListener {
            var mcurrentUser = FirebaseAuth.getInstance().currentUser!!
            var userID = mcurrentUser.uid

            mDatabase = FirebaseDatabase.getInstance().reference.child("users")
                .child(userID)

            var status = statusUpdateEtID.text.toString().trim()

            mDatabase.child("status").setValue(status).addOnCompleteListener {
                task ->
                if (task.isSuccessful){
                    Toast.makeText(this,"Status Updated Successfully", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this,settingsActivity::class.java))
                    finish()
                }else {
                    Toast.makeText(this,"Status Not Updated ", Toast.LENGTH_LONG).show()
                }
            }
        }






        }




    }
