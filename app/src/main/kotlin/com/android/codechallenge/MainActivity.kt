package com.android.codechallenge

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //test
        recyclerView = findViewById(R.id.recycler_view)

        val linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager

        val controller = Controller()
        controller.start()
    }

    interface API_CALL {
        @GET("/photos")
        fun getPhotos(): Call<List<JsonObject>>
    }

    inner class Controller : Callback<List<JsonObject>> {

        private val API_URL = "https://jsonplaceholder.typicode.com"

        lateinit var listAdapter: ListItemAdapter

        var listResult = ArrayList<ListItem>()

        fun start() {
            val retrofit = Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val api = retrofit.create(API_CALL::class.java)
            val call = api.getPhotos()
            call.enqueue(this)
        }

        override fun onFailure(call: Call<List<JsonObject>>, t: Throwable) {
            Log.v("DEV", "${t.message}")
        }

        override fun onResponse(call: Call<List<JsonObject>>, response: Response<List<JsonObject>>) {
            for (i in response.body()!!) {
                val item = ListItem(
                    id = i.get("id").asInt,
                    title = i.get("title").asString,
                    url = i.get("url").asString,
                    thumbnailUrl = i.get("thumbnailUrl").asString
                )
                listResult.add(item)
            }

            listAdapter = ListItemAdapter(listResult)
            recyclerView.adapter = listAdapter
        }
    }

    data class ListItem(
        val id: Int,
        val title: String,
        val url: String,
        val thumbnailUrl: String
    )

    class ListItemAdapter(val items: List<ListItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return ListItemAdapterViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_list_adapter,
                    parent,
                    false
                )
            )
        }

        override fun getItemCount(): Int = items.size

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            (holder as ListItemAdapterViewHolder).title.text = items[position].title
            Glide.with(holder.context).load(items[position].url).into(holder.image)
        }

        inner class ListItemAdapterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            var context = view.context
            var title = view.findViewById<TextView>(R.id.text_view)
            var image = view.findViewById<ImageView>(R.id.image_view)
        }
    }
}
