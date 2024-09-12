package com.example.bookapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookapp.adapter.BooksAdapter
import com.example.bookapp.databinding.FragmentHomeBinding
import com.example.bookapp.model.Book
import com.example.bookapp.service.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: BooksAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Set up RecyclerView
        binding.recyclerViewBooks.layoutManager = LinearLayoutManager(requireContext())

        // Fetch and display books
        fetchBooks()

        return root
    }

    private fun fetchBooks() {
        val authToken = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6Im1hdGhldXMuZGV2IiwiZXhwIjoxNzI2MTYxOTkxfQ.6uNGNBs63lv_LJOfnL_Au0gZ_NhW-QRPn67cvbwjIVQ"
        RetrofitClient.instance.getAllBooks(authToken).enqueue(object : Callback<List<Book>> {
            override fun onResponse(call: Call<List<Book>>, response: Response<List<Book>>) {
                if (response.isSuccessful && response.body() != null) {
                    val books = response.body()!!
                    adapter = BooksAdapter(books)
                    binding.recyclerViewBooks.adapter = adapter
                } else {
                    Toast.makeText(requireContext(), "Failed to load books", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Book>>, t: Throwable) {
                Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            println(t.message)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
