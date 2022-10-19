package com.astro.test.irwan.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.astro.test.irwan.core.domain.model.Github
import com.astro.test.irwan.databinding.LayoutListUserBinding

class MainAdapter :
    PagingDataAdapter<Github, MainAdapter.HomeViewHolder>(DIFF_CALLBACK) {

    var onItemClick: ((Github) -> Unit)? = null

    inner class HomeViewHolder(private val binding: LayoutListUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Github) {
            binding.data = data
            binding.executePendingBindings()

            binding.imgFavorite.setOnClickListener {
                onItemClick?.invoke(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding =
            LayoutListUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Github>() {
            override fun areItemsTheSame(
                oldItem: Github,
                newItem: Github
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: Github,
                newItem: Github
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

}