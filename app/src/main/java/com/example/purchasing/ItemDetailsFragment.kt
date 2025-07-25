package com.example.purchasing.fragments

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.purchasing.R

class ItemDetailsFragment : Fragment() {

    private var itemName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Get argument passed from previous fragment
        itemName = arguments?.getString("item_name")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item_details, container, false)

        val itemText = view.findViewById<TextView>(R.id.item_detail_text)
        itemText.text = itemName ?: "No item selected"

        return view
    }

    @Deprecated("Overrides deprecated method", level = DeprecationLevel.HIDDEN)
    @Suppress("DEPRECATION")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                requireActivity().onBackPressedDispatcher.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
