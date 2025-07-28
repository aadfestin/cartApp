package com.example.purchasing.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.purchasing.R
import com.example.purchasing.models.Transaction

class TransactionAdapter(
    private val transactions: List<Transaction>
) : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val description: TextView = itemView.findViewById(R.id.transaction_description)
        val date: TextView = itemView.findViewById(R.id.transaction_date)
        val method: TextView = itemView.findViewById(R.id.transaction_method)
        val amount: TextView = itemView.findViewById(R.id.transaction_amount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.transaction_item, parent, false)
        return TransactionViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = transactions[position]
        holder.description.text = transaction.description
        holder.date.text = transaction.date
        holder.method.text = transaction.method
        holder.amount.text = "₱${transaction.amount}"
    }

    override fun getItemCount(): Int = transactions.size
}
