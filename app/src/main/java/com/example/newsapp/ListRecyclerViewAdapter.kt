package com.example.newsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.Models.Article
import com.example.newsapp.Models.NewsApiResponse
import com.squareup.picasso.Picasso

class ListRecyclerViewAdapter(
    private val listOfNews:List<Article>,
    private val clickedItem: (Article) -> Unit
): RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.headline_list_items, parent, false)
        return MyViewHolder(listItem)
    }

    override fun getItemCount(): Int {
        return listOfNews.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(listOfNews[position], clickedItem)
    }
}

class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view){
    fun bind(news: Article, clickedItem: (Article) -> Unit){
        var title = view.findViewById<TextView>(R.id.text_title)
        var source = view.findViewById<TextView>(R.id.text_source)
        var imageView = view.findViewById<ImageView>(R.id.img_headline)
        title.text = news.title
        source.text = news.source.name
        if(news.urlToImage!=null){
            Picasso.get().load(news.urlToImage).into(imageView)
        }
        view.setOnClickListener {
            clickedItem(news)
        }

    }

}