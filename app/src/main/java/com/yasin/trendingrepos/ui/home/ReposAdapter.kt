package com.yasin.trendingrepos.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.yasin.trendingrepos.R
import com.yasin.trendingrepos.databinding.ListItemRepositoryBinding
import com.yasin.trendingrepos.ui.uiDataModel.RepositoryUi

/**
 * Created by Yasin on 29/4/20.
 */
class ReposAdapter(private val picasso: Picasso) : ListAdapter<RepositoryUi, ReposViewHolder>(ReposDiffItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReposViewHolder {
        return ReposViewHolder(
            ListItemRepositoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ReposViewHolder, position: Int) {
        val repo = currentList[position]
        holder.bind(repo,picasso)
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

class ReposViewHolder(private val binding: ListItemRepositoryBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        repo: RepositoryUi,
        picasso: Picasso
    ) {
        binding.tvRepoName.text = repo.fullName
        binding.tvRepoUpdatedAt.text = repo.pushedAt
        binding.tvRepoLanguage.text = repo.language
        binding.tvRepoDescription.text = repo.description
        picasso.load(repo.owner?.reposUrl)
            .placeholder(R.drawable.logo)
            .fit()
            .centerCrop()
            .into(binding.ivLogo
            )
    }
}