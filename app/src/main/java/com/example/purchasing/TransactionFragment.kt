package com.example.purchasing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.purchasing.adapters.TransactionAdapter
import com.example.purchasing.models.Transaction
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

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
        // loadTransactionsFromFirestore()
        addDummyTransactions()

    }



    private fun addDummyTransactions() {
        transactions.clear()
        transactions.add(
            Transaction(
                date = "2025-07-30",
                amount = 250.0,
            )
        )
        transactions.add(
            Transaction(
                date = "2025-07-29",
                amount = 100.0,

            )
        )
        adapter.notifyDataSetChanged()
    }


    private fun loadTransactionsFromFirestore() {
        val db = FirebaseFirestore.getInstance()
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        db.collection("transactions")
            .whereEqualTo("userId", currentUserId)
            .get()
            .addOnSuccessListener { documents ->
                transactions.clear()
                if (!documents.isEmpty) {
                    for (document in documents) {
                        val transaction = document.toObject(Transaction::class.java)
                        transactions.add(transaction)
                    }
                }
                adapter.notifyDataSetChanged()
                toggleEmptyState()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Failed to load transactions", Toast.LENGTH_SHORT).show()
            }
    }

    private fun toggleEmptyState() {
        val noTransactionsView = view?.findViewById<View>(R.id.no_transactions_text)
        if (transactions.isEmpty()) {
            recyclerView.visibility = View.GONE
            noTransactionsView?.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.VISIBLE
            noTransactionsView?.visibility = View.GONE
        }
    }



    fun refreshTransactions() {
        loadTransactionsFromFirestore()
    }

}
