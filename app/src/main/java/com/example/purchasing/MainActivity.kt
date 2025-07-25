package com.example.purchasing

import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.purchasing.adapters.MainPagerAdapter


class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        val viewPager = findViewById<ViewPager2>(R.id.viewPager)
        viewPager.adapter = MainPagerAdapter(this)

    }
}

