package com.example.chatapp.Repository

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.firestore.auth.User
import java.lang.Exception

class UserRepository {
    private var mCurrentUser: FirebaseUser = FirebaseAuth.getInstance().currentUser!!
    private var mAuth:FirebaseAuth = FirebaseAuth.getInstance()
    var userId = mCurrentUser.uid

    private val mDatabase:DatabaseReference = FirebaseDatabase.getInstance().reference.child("users").child(userId)
    @Volatile private var INSTANCE: UserRepository? = null

    fun getInstance() : UserRepository{
        return INSTANCE?: synchronized(this){
            val instance = UserRepository()
            INSTANCE = instance
            instance

        }
    }

//    fun loadUsers (userList: MutableLiveData<List<User>>){
//        mDatabase.addValueEventListener(object : ValueEventListener{
//            override fun onDataChange(snapshot: DataSnapshot) {
//                try {
//
//                } catch (e:Exception){
//                    Toast.makeText(this,"$e.")
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//
//        })
//
//    }
}


