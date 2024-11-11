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

class BooksAdapter(
    private val bookList: List<Book>,
    private val itemClickListener: (Book) -> Unit // Listener de clique para cada item
) : RecyclerView.Adapter<BooksAdapter.BookViewHolder>() {

    class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val authorTextView: TextView = itemView.findViewById(R.id.authorTextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        val coverImageView: ImageView = itemView.findViewById(R.id.imageView2)

        // Função para associar o listener de clique ao item
        fun bind(book: Book, clickListener: (Book) -> Unit) {
            titleTextView.text = book.title
            authorTextView.text = book.author
            descriptionTextView.text = book.year
            Glide.with(itemView.context)
                .load(book.photo)
                .into(coverImageView)

            // Definindo o clique no item
            itemView.setOnClickListener {
                clickListener(book) // Chama o listener com o item clicado
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_book, parent, false)
        return BookViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(bookList[position], itemClickListener) // Passa o item e o listener
    }

    override fun getItemCount(): Int {
        return bookList.size
    }
}
