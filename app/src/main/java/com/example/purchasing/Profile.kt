package com.example.purchasing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

// import android.widget.Toast

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nameText = view.findViewById<TextView>(R.id.profileName)
        val emailText = view.findViewById<TextView>(R.id.profileEmail)
        val editButton = view.findViewById<Button>(R.id.editProfileButton)

        // Dummy data: Add a user model and Database entries for example later on
        nameText.text = "Sophie"
        emailText.text = "sophix@gmail.com"

        editButton.setOnClickListener {
            // TODO: Edit and save changes
        }
    }

    private fun fetchWallet() {
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val db = FirebaseFirestore.getInstance()

        db.collection("users").document(currentUserId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val wallet = document.getDouble("wallet") ?: 0.0
                    // Update UI, e.g., walletTextView.text = "₱$wallet"
                }
            }
    }

    private fun deductWallet(amount: Double) {
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val db = FirebaseFirestore.getInstance()

        val userRef = db.collection("users").document(currentUserId)

        db.runTransaction { transaction ->
            val snapshot = transaction.get(userRef)
            val currentWallet = snapshot.getDouble("wallet") ?: 0.0
            if (currentWallet >= amount) {
                transaction.update(userRef, "wallet", currentWallet - amount)
            } else {
                throw Exception("Insufficient balance")
            }
        }.addOnSuccessListener {
            Toast.makeText(requireContext(), "Purchase successful!", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(requireContext(), "Not enough funds.", Toast.LENGTH_SHORT).show()
        }
    }


}
