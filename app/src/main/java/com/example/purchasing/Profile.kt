package com.example.purchasing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import android.widget.Toast

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

        // Dummy data: Add a user model and Database entries later
        nameText.text = "Sophie"
        emailText.text = "sophix@gmail.com"

        editButton.setOnClickListener {
            // TODO: Edit and save changes
        }
    }
}
