package com.example.purchasing.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.purchasing.CartFragment
import com.example.purchasing.HomeFragment
import com.example.purchasing.PaymentFragment
import com.example.purchasing.TransactionHistoryFragment

class MainPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> PaymentFragment()
            2 -> CartFragment()
            3 -> TransactionHistoryFragment()
            else -> throw IllegalArgumentException("Invalid position")
        }
    }
}
