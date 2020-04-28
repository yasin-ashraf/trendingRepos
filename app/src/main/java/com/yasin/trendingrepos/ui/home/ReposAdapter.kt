package com.yasin.trendingrepos.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yasin.trendingrepos.databinding.ListItemRepositoryBinding
import com.yasin.trendingrepos.ui.home.uiModel.RepositoryUi

/**
 * Created by Yasin on 29/4/20.
 */
class ReposAdapter : ListAdapter<RepositoryUi, ReposViewHolder>(ReposDiffItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReposViewHolder {
        return ReposViewHolder(
            ListItemRepositoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ReposViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 20
    }

}

class ReposDiffItemCallback : DiffUtil.ItemCallback<RepositoryUi>() {

    override fun areContentsTheSame(oldItem: RepositoryUi, newItem: RepositoryUi): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areItemsTheSame(oldItem: RepositoryUi, newItem: RepositoryUi): Boolean {
        return oldItem == newItem
    }
}

class ReposViewHolder(listItemBinding: ListItemRepositoryBinding) :
    RecyclerView.ViewHolder(listItemBinding.root)