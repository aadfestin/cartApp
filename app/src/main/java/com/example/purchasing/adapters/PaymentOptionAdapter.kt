package com.example.purchasing.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.purchasing.R
import com.example.purchasing.models.PaymentOption

class PaymentOptionAdapter(
    private val paymentOptions: MutableList<PaymentOption>,
    private val onPaymentSelected: (PaymentOption) -> Unit
) : RecyclerView.Adapter<PaymentOptionAdapter.PaymentViewHolder>() {

    private var selectedPosition = -1

    inner class PaymentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val icon: ImageView = itemView.findViewById(R.id.payment_icon)
        val name: TextView = itemView.findViewById(R.id.payment_name)
        val radio: RadioButton = itemView.findViewById(R.id.payment_radio)

        init {
            itemView.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    selectedPosition = position
                    notifyDataSetChanged()
                    onPaymentSelected(paymentOptions[position])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.payment_option, parent, false)
        return PaymentViewHolder(view)
    }

    override fun onBindViewHolder(holder: PaymentViewHolder, position: Int) {
        val option = paymentOptions[position]
        holder.name.text = option.name
        holder.icon.setImageResource(option.iconResId)
        holder.radio.isChecked = (position == selectedPosition)
    }

    override fun getItemCount(): Int = paymentOptions.size
}
