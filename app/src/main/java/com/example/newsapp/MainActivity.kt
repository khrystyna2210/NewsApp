package com.example.newsapp

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.opengl.Visibility
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.ProgressBar
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.Models.Article
import com.example.newsapp.Models.NewsApiResponse
import com.example.newsapp.Utilities.ApiUtilities
import com.example.newsapp.Utilities.OnFetchDataListener
import com.google.android.material.badge.BadgeUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), OnClickListener {

    private lateinit var recycler_main: RecyclerView
    private lateinit var progressBar: ConstraintLayout
    private lateinit var btn_1: Button
    private lateinit var btn_2: Button
    private lateinit var btn_3: Button
    private lateinit var btn_4: Button
    private lateinit var btn_5: Button
    private lateinit var btn_6: Button
    private lateinit var btn_7: Button
    private lateinit var searchNews: SearchView

    private fun init(){
        btn_1 = findViewById(R.id.btn_1)
        btn_2 = findViewById(R.id.btn_2)
        btn_3 = findViewById(R.id.btn_3)
        btn_4 = findViewById(R.id.btn_4)
        btn_5 = findViewById(R.id.btn_5)
        btn_6 = findViewById(R.id.btn_6)
        btn_7 = findViewById(R.id.btn_7)

        btn_1.setOnClickListener(this)
        btn_2.setOnClickListener(this)
        btn_3.setOnClickListener(this)
        btn_4.setOnClickListener(this)
        btn_5.setOnClickListener(this)
        btn_6.setOnClickListener(this)
        btn_7.setOnClickListener(this)

        recycler_main = findViewById(R.id.recycler_main)
        progressBar = findViewById(R.id.pb_loading)
        recycler_main.visibility = View.GONE
        progressBar.visibility = View.VISIBLE

        searchNews = findViewById(R.id.search_view)
        searchNews.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                getNewsHeadlines(listener, "general", p0)
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }

        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        getNewsHeadlines(listener, "general", null)
    }

    override fun onClick(view: View?) {
        var btn = view as Button
        getNewsHeadlines(listener, btn.text.toString(), null)
    }


    private var listener : OnFetchDataListener<NewsApiResponse> = object: OnFetchDataListener<NewsApiResponse>{
        override fun onFetchData(list: List<Article>, message: String) {

            if(list.isEmpty()){
                Toast.makeText(applicationContext, "No Data found!!!", Toast.LENGTH_SHORT).show()
            }else{
                recycler_main.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
                showNewsList(list)
            }

        }

        override fun onError(message: String) {
            Toast.makeText(applicationContext, "An Error Occured!!!", Toast.LENGTH_SHORT).show()
        }

    }

    private fun showNewsList(list: List<Article>) {
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_main)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ListRecyclerViewAdapter(list){
                selectedItem:Article -> displayURL(selectedItem)
        }
    }

    private fun displayURL(selectedItem: Article) {

        val intent = Intent(this, DetailsActivity::class.java)
        startActivity(intent.putExtra("data", selectedItem))
    }

    private fun getNewsHeadlines(listener: OnFetchDataListener<NewsApiResponse>, category:String, query:String?){
        ApiUtilities.getApiInterface()?.getNews("us",category, query, API_KEY)
            ?.enqueue(object: Callback<NewsApiResponse> {
                @RequiresApi(Build.VERSION_CODES.O)
                override fun onResponse(call: Call<NewsApiResponse>, response: Response<NewsApiResponse>) {
                    if(!response.isSuccessful){
                        Toast.makeText(this@MainActivity, "Error",Toast.LENGTH_SHORT).show()
                    }
                    listener.onFetchData(response.body()!!.articles, response.message())
                }

                override fun onFailure(call: Call<NewsApiResponse>, t: Throwable) {
                    listener.onError("Request failure!")
                }
            })
    }

    companion object{
        const val API_KEY="0e7841cb1e034b178ea36d177f6aad32"
    }


}