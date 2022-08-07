package com.example.chatapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chatapp.R
import com.google.firebase.database.DatabaseReference
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_settings.*

//pass users class and users adapter
//class UserAdapter(private val userList: DatabaseReference?, private val context: Context): RecyclerView.Adapter<UserAdapter.ViewHolder> () {
//
//
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.ViewHolder {
//        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.users_row,parent,false)
//        return ViewHolder(itemView)
//    }
//
//  override fun onBindViewHolder(holder:ViewHolder, position: Int) {
////       var currentItem = userList?.get(position)
////        holder.username.text = currentItem.display_name
////        holder.userstatus.text = currentItem.status
//////        holder.userProfilePic.setImageResource(currentItem.)
////        Glide.with(context).load(currentItem.thumb_image).dontAnimate().into(holder.userProfilePic)
//      return
//   }
//
//    override fun getItemCount() {
//       //return  userList.size
//        return
//    }
//
//    class ViewHolder(itemView:View): RecyclerView.ViewHolder (itemView) {
//
//        var username: TextView = itemView.findViewById(R.id.userName)
//        var userstatus: TextView = itemView.findViewById(R.id.userStatus)
//        var userProfilePic: CircleImageView = itemView.findViewById(R.id.usersProfile)
//
//
//
//
//    }
//}