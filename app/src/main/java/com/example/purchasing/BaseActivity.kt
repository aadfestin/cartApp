package com.example.purchasing

import android.content.Intent
import android.graphics.Color
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
import com.example.purchasing.adapters.MainPagerAdapter

open class BaseActivity : AppCompatActivity() {

    override fun setContentView(layoutResID: Int) {
        FirebaseApp.initializeApp(this)

        val baseLayout = layoutInflater.inflate(R.layout.activity_base, null)
        super.setContentView(baseLayout)

        // ----- Drawer + Toolbar -----
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.setTitleTextColor(Color.WHITE)
        setSupportActionBar(toolbar)

        val drawerToggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.drawer_open,
            R.string.drawer_close
        )
        drawerToggle.drawerArrowDrawable.color = ContextCompat.getColor(this, android.R.color.white)
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        // Drawer menu clicks
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

        // ----- ViewPager2 Setup -----
        val viewPager = findViewById<ViewPager2>(R.id.viewPager)
        viewPager.adapter = MainPagerAdapter(this)  // Ensure this includes PaymentFragment

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == 1) { // Cart Fragment
                    val cartFragment = supportFragmentManager.findFragmentByTag("f1") as? CartFragment
                    cartFragment?.refreshCart()
                }
                if (position == 3){
                    val transactionFragment = supportFragmentManager.findFragmentByTag("f3") as? TransactionHistoryFragment
                    transactionFragment?.refreshTransactions()
                }
            }
        })



        // ----- Bottom Button Navigation -----
        findViewById<ImageButton>(R.id.home_button).setOnClickListener {
            showViewPagerPage(0)
        }

        findViewById<ImageButton>(R.id.cart_button).setOnClickListener {
            showViewPagerPage(2)
        }


        findViewById<ImageButton>(R.id.transaction_button).setOnClickListener {
            showViewPagerPage(1)
        }

        findViewById<ImageButton>(R.id.payment_button).setOnClickListener {
            showViewPagerPage(3)
        }


    }

    private fun showViewPagerPage(pageIndex: Int) {
        val viewPager = findViewById<ViewPager2>(R.id.viewPager)
        val extraContainer = findViewById<View>(R.id.extra_fragment_container)

        extraContainer.visibility = View.GONE
        viewPager.visibility = View.VISIBLE
        viewPager.currentItem = pageIndex
    }


    private fun openFragment(fragment: Fragment) {
        val viewPager = findViewById<View>(R.id.viewPager)
        val extraContainer = findViewById<View>(R.id.extra_fragment_container)

        viewPager.visibility = View.GONE
        extraContainer.visibility = View.VISIBLE

        supportFragmentManager.beginTransaction()
            .replace(R.id.extra_fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

}
