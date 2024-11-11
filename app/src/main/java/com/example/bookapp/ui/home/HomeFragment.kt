package com.example.bookapp.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookapp.R
import com.example.bookapp.adapter.BooksAdapter
import com.example.bookapp.databinding.FragmentHomeBinding
import com.example.bookapp.model.Book
import com.example.bookapp.service.RetrofitClient
import com.example.bookapp.ui.bookDetail.BookDetailActivity
import com.example.bookapp.ui.dashboard.LoginActivity
import com.example.bookapp.ui.notifications.NotificationsFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment()  {

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
        val sharedPreferences = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token", "")
        val progressBar = binding.root.findViewById<View>(R.id.progressBar3)
        val authToken = "Bearer ${token}"
        RetrofitClient.instance.getAllBooks(authToken).enqueue(object : Callback<List<Book>> {
            override fun onResponse(call: Call<List<Book>>, response: Response<List<Book>>) {
                if (response.isSuccessful && response.body() != null) {
                    val books = response.body()!!
                    adapter = BooksAdapter(books) { selectedBook ->
                        // Ação ao clicar em um livro
                        val intent = Intent(requireContext(), BookDetailActivity::class.java)
                        intent.putExtra("book_id", selectedBook.id) // Enviar ID ou informações do livro
                        startActivity(intent)
                    }
                    binding.recyclerViewBooks.adapter = adapter
                } else {
                    if (response.code() === 400) {
                        val intent = Intent(requireContext(), LoginActivity::class.java)
                        startActivity(intent)
                    }
                    Toast.makeText(requireContext(), "Failed to load books", Toast.LENGTH_SHORT).show()
                }
                progressBar.visibility = View.GONE
            }

            override fun onFailure(call: Call<List<Book>>, t: Throwable) {
                progressBar.visibility = View.GONE
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
