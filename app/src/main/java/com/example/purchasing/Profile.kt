package com.example.purchasing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfileFragment : Fragment() {

    private lateinit var nameText: TextView
    private lateinit var emailText: TextView
    private lateinit var walletText: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nameText = view.findViewById(R.id.profileName)
        emailText = view.findViewById(R.id.profileEmail)
        walletText = view.findViewById(R.id.profileWallet)

        fetchUserProfile()
    }

    private fun fetchUserProfile() {
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val db = FirebaseFirestore.getInstance()

        db.collection("user").document(currentUserId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val name = document.getString("name") ?: "Unnamed"
                    val email = document.getString("email") ?: "No Email"
                    val wallet = document.getDouble("wallet")

                    nameText.text = name
                    emailText.text = email

                    if (wallet != null) {
                        walletText.text = getString(R.string.wallet_balance, wallet.toDouble())
                    }
                } else {
                    Toast.makeText(requireContext(), "User not found", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Failed to load profile", Toast.LENGTH_SHORT).show()
            }
    }
}
