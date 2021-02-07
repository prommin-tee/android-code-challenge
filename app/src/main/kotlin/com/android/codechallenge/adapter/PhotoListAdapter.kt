package com.android.codechallenge.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.codechallenge.R
import com.android.codechallenge.data.PhotoResponse
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_list_adapter.view.*

class PhotoListAdapter(
  private val photosList: List<PhotoResponse>,
  private val onItemClick: (PhotoResponse) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

  override fun getItemCount(): Int {
    return photosList.size
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    return PhotoVH(
      LayoutInflater.from(parent.context).inflate(R.layout.item_list_adapter, parent, false)
    )
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    (holder as PhotoVH).bind(photosList[position], position)
  }

  inner class PhotoVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(itemPhoto: PhotoResponse, position: Int) {

      itemView.text_view.text = itemPhoto.title

      Picasso.get().load(itemPhoto.url).into(itemView.image_view)

      itemView.rootContent.setOnClickListener {
        onItemClick.invoke(itemPhoto)
      }
    }
  }

}