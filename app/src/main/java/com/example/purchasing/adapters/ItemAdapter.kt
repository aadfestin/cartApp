package com.example.purchasing.adapters

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.purchasing.utils.Cart
import com.example.purchasing.R
import com.example.purchasing.models.Item

class ItemAdapter(
    private val itemList: MutableList<Item>,
    private val context: Context,
    private val isInCart: Boolean = false // hide delete button.
) : RecyclerView.Adapter<ItemAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val text: TextView = itemView.findViewById(R.id.item_text)
        val addButton: ImageButton = itemView.findViewById(R.id.add_button)
        val removeButton: ImageButton = itemView.findViewById(R.id.remove_button)
        val deleteButton: ImageButton = itemView.findViewById(R.id.delete_button)
        val price: TextView = itemView.findViewById(R.id.item_price)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = itemList[position]

        holder.text.text = item.name
        holder.price.text = context.getString(R.string.price_format, item.price)

        holder.addButton.setOnClickListener {
            Cart.add(item)
            Toast.makeText(context, "${item.name} added to cart", Toast.LENGTH_SHORT).show()
        }

        holder.removeButton.setOnClickListener {
            Cart.remove(item)
            Toast.makeText(holder.itemView.context, "${item.name} removed from cart", Toast.LENGTH_SHORT).show()

        }

        holder.deleteButton.visibility = if (isInCart) View.GONE else View.VISIBLE

        holder.deleteButton.setOnClickListener {
            val currentPosition = holder.bindingAdapterPosition
            if (currentPosition != RecyclerView.NO_POSITION) {
                val itemToDelete = itemList[currentPosition]

                AlertDialog.Builder(context)
                    .setTitle("Delete Item")
                    .setMessage("Are you sure you want to delete this item?")
                    .setPositiveButton("Yes") { _, _ ->
                        Cart.removeAllWithId(itemToDelete.id)
                        itemList.removeAt(currentPosition)
                        notifyItemRemoved(currentPosition)
                        Toast.makeText(context, "${itemToDelete.name} deleted", Toast.LENGTH_SHORT).show()
                    }
                    .setNegativeButton("No", null)
                    .show()
            }
        }



    }

    fun addItem(item: Item) {
        itemList.add(item)
        notifyItemInserted(itemList.size - 1)
    }

    fun updateItem(updatedItem: Item) {
        val index = itemList.indexOfFirst { it.id == updatedItem.id }
        if (index != -1) {
            itemList[index] = updatedItem
            notifyItemChanged(index)
        }
    }

    fun removeItem(item: Item) {
        val index = itemList.indexOfFirst { it.id == item.id }
        if (index != -1) {
            itemList.removeAt(index)
            notifyItemRemoved(index)
        }
    }




    override fun getItemCount(): Int = itemList.size
}
