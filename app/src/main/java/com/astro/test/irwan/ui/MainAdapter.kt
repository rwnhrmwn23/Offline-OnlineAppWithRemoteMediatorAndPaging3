package com.astro.test.irwan.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.astro.test.irwan.core.data.source.local.entity.GithubEntity
import com.astro.test.irwan.databinding.LayoutListUserBinding

class MainAdapter :
    PagingDataAdapter<GithubEntity, MainAdapter.HomeViewHolder>(DIFF_CALLBACK) {

    var onItemClick: ((GithubEntity) -> Unit)? = null

    inner class HomeViewHolder(private val binding: LayoutListUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: GithubEntity) {
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
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<GithubEntity>() {
            override fun areItemsTheSame(
                oldItem: GithubEntity,
                newItem: GithubEntity
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: GithubEntity,
                newItem: GithubEntity
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

}