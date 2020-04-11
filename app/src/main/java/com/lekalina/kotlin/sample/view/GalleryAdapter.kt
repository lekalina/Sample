package com.lekalina.kotlin.sample.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lekalina.kotlin.sample.data.Album
import com.lekalina.kotlin.sample.databinding.GalleryItemBinding
import com.lekalina.kotlin.sample.viewmodel.GalleryItemViewModel

class GalleryAdapter(private val items: List<GalleryItemViewModel>,
                     private var clickListener: OnItemClickListener
) : RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = GalleryItemBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position], clickListener)

    inner class ViewHolder(private val binding: GalleryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GalleryItemViewModel, listener: OnItemClickListener) {
            binding.viewmodel = item
            binding.executePendingBindings()
            binding.root.setOnClickListener {
                listener.onItemClick(binding.root, item.album)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, item: Album)
    }
}