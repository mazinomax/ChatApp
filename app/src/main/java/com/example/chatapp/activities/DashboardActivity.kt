package com.example.chatapp.activities

import android.content.Intent
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.example.chatapp.adapter.SectionPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.example.chatapp.R
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase

class DashboardActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var refUsers: DatabaseReference
    private lateinit var sectionAdapter: SectionPagerAdapter
    private lateinit var tabs: TabLayout
    private lateinit var viewpager: ViewPager2
    private lateinit var mCurrentUser: FirebaseUser
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        mAuth = FirebaseAuth.getInstance()

        readData()

        if (intent.extras != null) {
            var username = intent.extras!!.get("name")
            Toast.makeText(this,username.toString(),Toast.LENGTH_LONG).show()


        }
        // changing title of dashboard
        supportActionBar!!.title = "DashBoard"
       // dashViewPagerID!!.adapter = SectionPagerAdapter(this)
         viewpager = findViewById<ViewPager2>(R.id.dashViewPagerID)
        tabs = findViewById<TabLayout>(R.id.dashTabsId)

        sectionAdapter = SectionPagerAdapter(supportFragmentManager,lifecycle)
        //dashViewPagerID!!.adapter = sectionAdpter
        viewpager.adapter= sectionAdapter

        TabLayoutMediator(tabs, viewpager){ tab, index ->
            tab.text = when(index) {
                0 -> {"Chats"}
                1 -> {"Friends"}
                else -> { throw Resources.NotFoundException("position not found...")}
            }
        }.attach()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main_menu,menu)

        return true
    }
 // menu

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
             super.onOptionsItemSelected(item)


            if (item.itemId == R.id.logoutID){
                // log user out
                FirebaseAuth.getInstance().signOut()
                // take user to mainactivity after signing out
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            if (item.itemId == R.id.menuSettingsID){
                // take user to settings activity
                startActivity(Intent(this, settingsActivity::class.java))
                finish()
            }

        return true
    }

    private fun readData() {

        mCurrentUser = FirebaseAuth.getInstance().currentUser!!
        var userId = mCurrentUser.uid
        refUsers = FirebaseDatabase.getInstance().reference.child("users").child(userId)

        refUsers.get().addOnSuccessListener {
            if (it.exists()){
                val displayName = it.child("display_name").value
                val status = it.child("status").value
                val image = it.child("image").value
                Log.d("displayname",displayName.toString())
                Log.d("status",status.toString())
                Log.d("image",image.toString())

            } else {
                Toast.makeText(this, "user does not exist", Toast.LENGTH_LONG).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "failed", Toast.LENGTH_LONG).show()
        }
    }

}