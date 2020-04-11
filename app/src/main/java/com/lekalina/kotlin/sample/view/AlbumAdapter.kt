package com.lekalina.kotlin.sample.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lekalina.kotlin.sample.data.Images
import com.lekalina.kotlin.sample.databinding.AlbumItemBinding
import com.lekalina.kotlin.sample.viewmodel.AlbumItemViewModel

class AlbumAdapter(private val items: List<AlbumItemViewModel>,
                   private var clickListener: OnItemClickListener
) : RecyclerView.Adapter<AlbumAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AlbumItemBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position], clickListener)

    inner class ViewHolder(private val binding: AlbumItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AlbumItemViewModel, listener: OnItemClickListener) {
            binding.viewmodel = item
            binding.executePendingBindings()
            binding.root.setOnClickListener { view ->
                listener.onItemClick(binding.root, item.image)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, item: Images)
    }
}