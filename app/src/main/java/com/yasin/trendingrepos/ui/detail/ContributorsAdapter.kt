package com.yasin.trendingrepos.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.yasin.trendingrepos.R
import com.yasin.trendingrepos.data.models.Owner
import com.yasin.trendingrepos.databinding.ListItemContributorBinding

/**
 * Created by Yasin on 29/4/20.
 */
class ContributorsAdapter(private val picasso: Picasso,
                          private val onItemSelectListener: OnItemSelectListener
) : ListAdapter<Owner, ReposViewHolder>(OwnerDiffItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReposViewHolder {
        return ReposViewHolder(
            ListItemContributorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ReposViewHolder, position: Int) {
        val owner = currentList[position]
        holder.bind(owner, picasso)
        holder.itemView.setOnClickListener { onItemSelectListener.onSelect(owner.id ?: 0) }
    }

}

class OwnerDiffItemCallback : DiffUtil.ItemCallback<Owner>() {

    override fun areContentsTheSame(oldItem: Owner, newItem: Owner): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areItemsTheSame(oldItem: Owner, newItem: Owner): Boolean {
        return oldItem == newItem
    }
}

class ReposViewHolder(private val binding: ListItemContributorBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        owner: Owner,
        picasso: Picasso
    ) {
        binding.tvLoginName.text = owner.login
        binding.tvContributions.text = owner.contributions
        picasso.load(owner.avatarUrl)
            .placeholder(R.drawable.logo)
            .fit()
            .centerCrop()
            .into(binding.ivAvatar)
    }
}

interface OnItemSelectListener {
    fun onSelect(id: Int)
}