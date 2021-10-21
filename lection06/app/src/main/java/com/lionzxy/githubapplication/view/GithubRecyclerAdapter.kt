package com.lionzxy.githubapplication.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lionzxy.githubapplication.model.GithubRepo
import com.lionzxy.githubapplication.databinding.ItemRepoBinding

class GithubRecyclerAdapter : RecyclerView.Adapter<GithubRecyclerAdapter.GithubViewHolder>() {
    private val repos = mutableListOf<GithubRepo>()
    fun onNewItem(inputRepos: List<GithubRepo>) {
        val position = repos.size
        repos.addAll(inputRepos)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubViewHolder {
        val binding = ItemRepoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GithubViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GithubViewHolder, position: Int) {
        holder.bind(repos[position])
    }

    override fun getItemCount() = repos.size

    class GithubViewHolder(
        private val binding: ItemRepoBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(repo: GithubRepo) {
            binding.title.text = repo.name
        }
    }
}
