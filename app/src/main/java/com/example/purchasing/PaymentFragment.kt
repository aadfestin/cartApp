package com.example.purchasing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.purchasing.adapters.PaymentOptionAdapter
import com.example.purchasing.models.PaymentOption

class PaymentFragment : Fragment() {

    private lateinit var paymentRecyclerView: RecyclerView
    private lateinit var paymentAdapter: PaymentOptionAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_payment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        paymentRecyclerView = view.findViewById(R.id.paymentRecyclerView)
        paymentRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Payment options
        val paymentOptions = mutableListOf(
            PaymentOption("1", userId = "", name = "Credit Card", iconResId = R.drawable.credit_card_24px),
            PaymentOption("2", userId = "", name = "Cash", iconResId = R.drawable.payments_24px)
        )


        paymentAdapter = PaymentOptionAdapter(paymentOptions) { selectedOption ->
            Toast.makeText(requireContext(), "Selected: ${selectedOption.name}", Toast.LENGTH_SHORT).show()
        }

        paymentRecyclerView.adapter = paymentAdapter

    }
}
