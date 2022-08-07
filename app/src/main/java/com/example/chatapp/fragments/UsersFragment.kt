package com.example.chatapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapp.Models.Users
import com.example.chatapp.R
//import com.example.chatapp.adapter.UserAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.fragment_users.*


class UsersFragment : Fragment() {
    //private lateinit var adapter: UserAdapter
    private lateinit var userArrayList: ArrayList<Users>
     private lateinit var userDatabase: DatabaseReference
    private lateinit var mStorageRef: StorageReference
    private lateinit var mStorage: FirebaseStorage
    private lateinit var mCurrentUser: FirebaseUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_users, container, false)

        mCurrentUser = FirebaseAuth.getInstance().currentUser!!
        var userId = mCurrentUser.uid
//       readData()


    }

//    private fun readData() {
//
//        mCurrentUser = FirebaseAuth.getInstance().currentUser!!
//        var userId = mCurrentUser.uid
//        userDatabase = FirebaseDatabase.getInstance().reference.child("users").child(userId)
//
//        userDatabase.get().addOnSuccessListener {
//            if (it.exists()){
//                val displayName = it.child("display_name").value
//                val status = it.child("status").value
//                val image = it.child("image").value
//                Log.d("displayname",displayName.toString())
//                Log.d("status",status.toString())
//                Log.d("image",image.toString())
//
//            } else {
//                Toast.makeText(activity, "user does not exist", Toast.LENGTH_LONG).show()
//            }
//        }.addOnFailureListener {
//            Toast.makeText(activity, "failed", Toast.LENGTH_LONG).show()
//        }
//    }


//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        var linearLayoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
//        userDatabase = FirebaseDatabase.getInstance().reference.child("users")
//        userRecyclerView.setHasFixedSize(true)
//
//        userRecyclerView.layoutManager = linearLayoutManager
//        userRecyclerView.adapter = UserAdapter(userDatabase,context!!)
//    }

}


