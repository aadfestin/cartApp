package com.example.purchasing.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.purchasing.R
import com.example.purchasing.models.Item
import com.example.purchasing.utils.Cart

class CartAdapter(
    private val cartItems: MutableList<Item>,
    private val context: Context
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemText: TextView = itemView.findViewById(R.id.item_text)
        val removeButton: ImageButton = itemView.findViewById(R.id.remove_button)
        val addButton: ImageButton = itemView.findViewById(R.id.add_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = cartItems[position]
        holder.itemText.text = "${item.name} x${item.quantity}"

        holder.addButton.setOnClickListener {
            Cart.add(item)
            notifyItemChanged(holder.bindingAdapterPosition)
            Toast.makeText(context, "${item.name} added to cart", Toast.LENGTH_SHORT).show()
        }

        holder.removeButton.setOnClickListener {
            Cart.remove(item)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, cartItems.size)
            Toast.makeText(context, "${item.name} removed from cart", Toast.LENGTH_SHORT).show()
        }

        val deleteButton: ImageButton? = holder.itemView.findViewById(R.id.delete_button)
        deleteButton?.visibility = View.GONE

    }

    override fun getItemCount(): Int = cartItems.size
}