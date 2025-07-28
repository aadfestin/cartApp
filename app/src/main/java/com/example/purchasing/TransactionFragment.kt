package com.example.purchasing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.purchasing.adapters.TransactionAdapter
import com.example.purchasing.models.Transaction

class TransactionHistoryFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TransactionAdapter
    private val transactions = mutableListOf<Transaction>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_transaction, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.transactionRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = TransactionAdapter(transactions)
        recyclerView.adapter = adapter

        // TODO: Load transaction history from Firestore or local storage
        loadDummyTransactions()
    }

    private fun loadDummyTransactions() {
        // TODO: replace sample data
        transactions.add(Transaction("Order #1001", "2025-07-25", "Cash",500.0))
        transactions.add(Transaction("Order #1002", "2025-07-24", "Cash", 250.0))
        transactions.add(Transaction("Order #1003", "2025-07-23", "Card",300.0))
        adapter.notifyDataSetChanged()
    }
}
