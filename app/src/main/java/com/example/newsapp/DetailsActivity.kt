package com.example.newsapp

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.newsapp.Models.Article
import com.squareup.picasso.Picasso

class DetailsActivity : AppCompatActivity() {

    private lateinit var text_detail_title: TextView
    private lateinit var text_detail_author: TextView
    private lateinit var text_detail_time: TextView
    private lateinit var text_detail_detail: TextView
    private lateinit var text_detail_content: TextView
    private lateinit var img_detail_news: ImageView
    private lateinit var btn_browser: Button

    private fun init(){
        text_detail_title = findViewById(R.id.text_detail_title)
        text_detail_author = findViewById(R.id.text_detail_author)
        text_detail_time = findViewById(R.id.text_detail_time)
        text_detail_detail = findViewById(R.id.text_detail_detail)
        text_detail_content = findViewById(R.id.text_detail_content)
        img_detail_news = findViewById(R.id.img_detail_news)
        btn_browser = findViewById(R.id.btn_show_in_br)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        init()
        val article = intent.extras?.getSerializable("data",Article::class.java)
        if(article!=null){
            fillContent(article)
        }

    }

    private fun fillContent(article: Article) {
        text_detail_title.text = article.title
        text_detail_author.text = article.author
        text_detail_time.text = article.publishedAt
        text_detail_detail.text = article.description
        text_detail_content.text = article.content
        if(article.urlToImage!=null){
            Picasso.get().load(article.urlToImage).into(img_detail_news)
        }


        btn_browser.setOnClickListener {
            var browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url));
            startActivity(browserIntent);
        }
    }
}