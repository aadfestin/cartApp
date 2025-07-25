package com.example.purchasing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.purchasing.adapters.CartAdapter
import com.example.purchasing.utils.Cart

class CartFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_cart, container, false)

    fun refreshCart() {
        adapter.notifyDataSetChanged()
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.cartRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = CartAdapter(Cart.items, requireContext())
        recyclerView.adapter = adapter


    }
}

