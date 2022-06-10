package com.example.chatapp

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.example.chatapp.adapter.SectionPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_dashboard.view.*

class DashboardActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var refUsers: DatabaseReference
    private lateinit var sectionAdapter: SectionPagerAdapter
    private lateinit var tabs: TabLayout
    private lateinit var viewpager: ViewPager2
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        mAuth = FirebaseAuth.getInstance()

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

        TabLayoutMediator(tabs!!, viewpager){ tab, index ->
            tab.text = when(index) {
                0 -> {"Chats"}
                1 -> {"Users"}
                else -> { throw Resources.NotFoundException("position not found...")}
            }
        }.attach()

    }

}