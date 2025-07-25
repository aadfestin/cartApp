package com.example.purchasing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.purchasing.adapters.ItemAdapter
import com.example.purchasing.models.Item
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ItemAdapter
    private var listenerRegistration: ListenerRegistration? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.mainlayout, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = ItemAdapter(mutableListOf(), requireContext())
        recyclerView.adapter = adapter

        val db = FirebaseFirestore.getInstance()

        // Listen for real-time updates
        listenerRegistration = db.collection("item")
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    Toast.makeText(requireContext(), "Failed to fetch items", Toast.LENGTH_SHORT).show()
                    return@addSnapshotListener
                }

                if (snapshots != null) {
                    for (change in snapshots.documentChanges) {
                        val item = change.document.toObject(Item::class.java)
                        when (change.type) {
                            com.google.firebase.firestore.DocumentChange.Type.ADDED -> {
                                adapter.addItem(item)
                            }
                            com.google.firebase.firestore.DocumentChange.Type.MODIFIED -> {
                                adapter.updateItem(item)
                            }
                            com.google.firebase.firestore.DocumentChange.Type.REMOVED -> {
                                adapter.removeItem(item)
                            }
                        }
                    }
                }
            }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        listenerRegistration?.remove() // Stop listening when fragment is destroyed
    }
}
