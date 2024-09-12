package com.example.bookapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bookapp.R
import com.example.bookapp.model.Book

class BooksAdapter(private val bookList: List<Book>) :
    RecyclerView.Adapter<BooksAdapter.BookViewHolder>() {

    class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val authorTextView: TextView = itemView.findViewById(R.id.authorTextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        val coverImageView: ImageView = itemView.findViewById(R.id.imageView2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_book, parent, false)
        return BookViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = bookList[position]
        holder.titleTextView.text = book.title
        holder.authorTextView.text = book.author
        holder.descriptionTextView.text = book.year
        Glide.with(holder.itemView.context)
            .load(book.photo)  // Replace 'imageUrl' with the actual image field in the Book model
            .into(holder.coverImageView) // Load into the ImageView
    }

    override fun getItemCount(): Int {
        return bookList.size
    }
}
