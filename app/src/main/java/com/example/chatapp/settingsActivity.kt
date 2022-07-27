package com.example.chatapp

import android.app.Activity
import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import id.zelory.compressor.Compressor
//import id.zelory.compressor.Compressor.compress
import kotlinx.android.synthetic.main.activity_settings.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.util.*
import kotlin.collections.HashMap
import android.net.Uri as Uri1

class settingsActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDatabase: DatabaseReference
    private lateinit var mStorageRef: StorageReference
    private lateinit var mStorage: FirebaseStorage
    private lateinit var mCurrentUser:FirebaseUser
    private lateinit var imageuri: Uri1
    var galleryId: Int = 203
    //private lateinit var mCurrentUser: FirebaseUser


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        mStorage = FirebaseStorage.getInstance()
        mAuth = FirebaseAuth.getInstance()
        mStorageRef = FirebaseStorage.getInstance().reference
        mCurrentUser = FirebaseAuth.getInstance().currentUser!!
        var userId = mCurrentUser.uid
        mDatabase = FirebaseDatabase.getInstance().reference.child("users").child(userId)

        mDatabase.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                  var displayname = dataSnapshot.child("display_name").value
                  var userStatus = dataSnapshot.child("status").value
                  var thumbImage = dataSnapshot.child("thumb_image").value.toString()
                  var image = dataSnapshot.child("image").value.toString()

                settDisplayID.text = displayname.toString()
                settstatusTVID.text = userStatus.toString()
                Log.d("img", thumbImage)
                if (image.isNotEmpty()){
                    Log.d("picaso","CHECKED")
//
//                    Picasso.get()
//                        .load(image)
//                      //  .networkPolicy(NetworkPolicy.OFFLINE)
//                        .placeholder(R.drawable.profile_img)
//                        .resize(100,100)
//                        .into(settprofileID)

                    Log.d("picaso",image)
//                    Glide.with(applicationContext).load(image)
//                        .placeholder(R.drawable.profile_img)
//                        .dontAnimate()
//                        .into(settprofileID)
                    Glide.with(applicationContext).load(thumbImage).dontAnimate().into(settprofileID)
                }

            }
            override fun onCancelled(databaseErrorSnapshot: DatabaseError) {
            }
        })


        settChangeStatusButnID.setOnClickListener {
            var statusintent = Intent(this,StatusActivity::class.java)
            statusintent.putExtra("status",settstatusTVID.text.toString().trim())
            startActivity(statusintent)
        }

        settChangePixButnID.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "SELECT PICTURE"), galleryId)
        }

    }
   

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mStorage = FirebaseStorage.getInstance()
        mAuth = FirebaseAuth.getInstance()
        mStorageRef = FirebaseStorage.getInstance().reference
        mCurrentUser = FirebaseAuth.getInstance().currentUser!!
        var userId = mCurrentUser.uid
        mDatabase = FirebaseDatabase.getInstance().reference.child("users").child(userId)


        Log.d("data2", requestCode.toString())
            if (requestCode == galleryId && resultCode == Activity.RESULT_OK) {
                var image = data?.data
                CropImage.activity(image)
                    .setAspectRatio(1, 1)
                    .start(this)
            }

            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                val result = CropImage.getActivityResult(data)

                if (resultCode == Activity.RESULT_OK) {
                    val resultUri = result!!.uri
                    var userId = mCurrentUser.uid
                    var thumbFile = File(resultUri!!.path!!)

                    var thumbBitmap = Compressor(this)
                            .setMaxWidth(200)
                            .setMaxHeight(200)
                            .setQuality(65)
                            //.setCompressFormat(Bitmap.CompressFormat.WEBP_LOSSLESS)
                            .compressToBitmap(thumbFile)
                   // thumbBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri)
                    Log.d("uri22", resultUri.toString())
                    var byteArray = ByteArrayOutputStream()
                    thumbBitmap.compress(Bitmap.CompressFormat.JPEG,100, byteArray)

                    var thumbByteArray: ByteArray
                    thumbByteArray = byteArray.toByteArray()

                    var filePath = mStorageRef.child("child_profile_images")
                        .child(userId + ".jpg")
                    // create another directory for thumb images or smaller images

                    var thumbFilePath = mStorageRef.child("child_profile_images")
                        .child("thumbs")
                        .child(userId + ".jpg")

                    //put file in database
                    filePath.putFile(resultUri!!).addOnCompleteListener {
                                //task: Task<UploadTask.TaskSnapshot> ->
                                ///taskSnapshot ->
                        if (it.isSuccessful){
                                //lets get the pix url
                               // var downloadurl = task.result.storage.downloadUrl.toString()
                            filePath.downloadUrl.addOnCompleteListener () { taskSnapshot ->
                                var downloadurl = taskSnapshot.result.toString()
                                Log.d("uri", resultUri.toString())
                                Log.d("uri22", downloadurl)
                                // upload task

                                var uploadTask: UploadTask = thumbFilePath.putBytes(thumbByteArray)
                                Log.d("uri222", uploadTask.toString())

                                uploadTask.addOnCompleteListener { task: Task<UploadTask.TaskSnapshot> ->
                                    var thumbUrl = taskSnapshot.result.toString()
                                    Log.d("url2", downloadurl)


                                    if (task.isSuccessful) {
                                        val updateObj = HashMap<String, Any>()
                                        updateObj.put("image", downloadurl)
                                        updateObj["thumb_image"] = thumbUrl
                                        // we save image

                                        mDatabase.updateChildren(updateObj)
                                            .addOnCompleteListener {
                                                if (it.isSuccessful) {
                                                    Toast.makeText(
                                                        this,
                                                        "Image Saved",
                                                        Toast.LENGTH_LONG
                                                    ).show()
                                                } else {
                                                    Toast.makeText(
                                                        this,
                                                        "Image Not Saved",
                                                        Toast.LENGTH_LONG
                                                    ).show()
                                                }
                                            }
                                    } else {
                                        Toast.makeText(
                                            this,
                                            "Error",
                                            Toast.LENGTH_LONG
                                        ).show()


                                    }
                                }
                            }
                        }
                            //}

                    }
                }
            }



    }

//    private fun uploadImageToFirebase(fileUri: Uri?) {
//        if (fileUri != null) {
//            val fileName = UUID.randomUUID().toString() +".jpg"
//
//            val database = FirebaseDatabase.getInstance()
//            mStorageRef = FirebaseStorage.getInstance().reference.child("images/$fileName")
//
//            mStorageRef.putFile(fileUri)
//                .addOnSuccessListener(
//                    OnSuccessListener<UploadTask.TaskSnapshot> { taskSnapshot ->
//                        taskSnapshot.storage.downloadUrl.addOnSuccessListener {
//                            val imageUrl = it.toString()
//                        }
//                    })
//
//                .addOnFailureListener(OnFailureListener { e ->
//                    print(e.message)
//                })
//        }
   // }
    }



