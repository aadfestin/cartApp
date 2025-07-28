package com.example.purchasing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.purchasing.adapters.CartAdapter
import com.example.purchasing.utils.Cart

class CartFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CartAdapter
    private lateinit var totalPriceView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_cart, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.cartRecyclerView)
        totalPriceView = view.findViewById(R.id.cart_total_price)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = CartAdapter(Cart.items, requireContext()) {
            updateTotalPrice()
        }
        recyclerView.adapter = adapter

        updateTotalPrice()
    }

    private fun updateTotalPrice() {
        totalPriceView.text = getString(R.string.total_price_format, Cart.getTotalPrice())
    }

    fun refreshCart() {
        adapter.notifyDataSetChanged()
        updateTotalPrice()
    }
}


