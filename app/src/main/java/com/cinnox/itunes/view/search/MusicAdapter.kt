package com.cinnox.itunes.view.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cinnox.itunes.databinding.ItemMusicBinding
import com.cinnox.itunes.entity.MusicEntity

class MusicAdapter : RecyclerView.Adapter<MusicAdapter.ViewHolder>() {

    private val list = mutableListOf<MusicEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewBinding = ItemMusicBinding.inflate(inflater)
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount() = list.size

    class ViewHolder(private val viewBinding: ItemMusicBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(item: MusicEntity) {
            Glide.with(viewBinding.root).load(item.coverUrl).into(viewBinding.ivCover)
            viewBinding.tvTrackName.text = item.trackName
            viewBinding.tvArtisName.text = item.artisName
        }
    }

    fun replaceDataAll(data: List<MusicEntity>) {
        val oldSize = list.size

        list.clear()
        notifyItemRangeRemoved(0, oldSize)
        list.addAll(data)
        notifyItemRangeInserted(0, data.size)
    }

}