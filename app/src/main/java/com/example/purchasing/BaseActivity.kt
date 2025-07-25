package com.example.purchasing

import android.content.Intent
import android.graphics.Color
import android.provider.ContactsContract
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.navigation.NavigationView
import com.google.firebase.FirebaseApp
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment

open class BaseActivity : AppCompatActivity() {


    override fun setContentView(layoutResID: Int) {
        FirebaseApp.initializeApp(this)

        val baseLayout = layoutInflater.inflate(R.layout.activity_base, null)
        super.setContentView(baseLayout)

        // ----- Drawer + Toolbar -----
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar)

        val drawerToggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.drawer_open,
            R.string.drawer_close
        )
        drawerToggle.drawerArrowDrawable.color = ContextCompat.getColor(this, android.R.color.white)
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()


        // menu item clicks
        findViewById<NavigationView>(R.id.nav_view)
            .setNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.nav_profile -> {
                        openFragment(ProfileFragment())
                    }
                    R.id.nav_logout -> {
                        val i = Intent(this, LoginActivity::class.java)
                        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(i)
                    }
                }
                drawerLayout.closeDrawers()
                true
            }


        // Get reference to ViewPager2 from the base layout
        val viewPager = findViewById<ViewPager2>(R.id.viewPager)

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == 1) { // Cart Fragment
                    val cartFragment = supportFragmentManager.findFragmentByTag("f1") as? CartFragment
                    cartFragment?.refreshCart()
                }
            }
        })


        //These go to respective fragments

        findViewById<ImageButton>(R.id.home_button).setOnClickListener {
            viewPager.currentItem = 0
        }

        findViewById<ImageButton>(R.id.cart_button).setOnClickListener {
            viewPager.currentItem = 1
        }

        findViewById<ImageButton>(R.id.payment_button).setOnClickListener {
            viewPager.currentItem = 2
        }



    }

    private fun openFragment(fragment: Fragment) {
        // Hide ViewPager and show the extra container
        findViewById<View>(R.id.viewPager).visibility = View.GONE
        findViewById<View>(R.id.extra_fragment_container).visibility = View.VISIBLE

        supportFragmentManager.beginTransaction()
            .replace(R.id.extra_fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

}
