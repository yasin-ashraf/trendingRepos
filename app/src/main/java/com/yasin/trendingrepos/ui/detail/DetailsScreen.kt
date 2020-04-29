package com.yasin.trendingrepos.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import com.yasin.trendingrepos.R
import com.yasin.trendingrepos.data.models.Owner
import com.yasin.trendingrepos.databinding.ScreenDetailsBinding
import com.yasin.trendingrepos.getAppComponent
import com.yasin.trendingrepos.network.NetworkState
import com.yasin.trendingrepos.utils.*
import javax.inject.Inject

/**
 * Created by Yasin on 29/4/20.
 */
class DetailsScreen : Fragment() {

    @Inject lateinit var detailViewModelFactory: DetailViewModelFactory
    @Inject lateinit var picasso: Picasso
    private lateinit var detailsViewModel: DetailsViewModel
    private lateinit var binding : ScreenDetailsBinding
    private val contributorsAdapter : ContributorsAdapter by lazy {
        ContributorsAdapter(picasso,onItemSelectListener)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getAppComponent(requireContext()).injectDetailScreen(this)
        configureViewModel()
    }

    private fun observeViewState() {
        observeDetails()
        observeContributors()
    }

    private fun observeContributors() {
        detailsViewModel.contributors.observe(this.viewLifecycleOwner, Observer {
            when(it) {
                is NetworkState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.errorLayout.visibility = View.INVISIBLE
                }
                is NetworkState.Error -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    binding.errorLayout.visibility = View.VISIBLE
                }
                is NetworkState.Success -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    binding.errorLayout.visibility = View.GONE
                    contributorsAdapter.submitList(it.data)
                }
            }
        })
    }

    private fun observeDetails() {
        detailsViewModel.repoDetailsDb.observe(this.viewLifecycleOwner, Observer { repo ->
            if(repo != null) {
                binding.tvRepoName.text = repo.name
                binding.tvRepoUpdatedAt.text = repo.pushedAt.dateToFormat()
                binding.tvRepoLanguage.text = repo.language
                binding.tvRepoDescription.text = repo.description
                binding.tvRepoWatchers.text = repo.watchersCount.toString()
                binding.title.text = repo.fullName
                picasso.load(repo.owner?.avatarUrl)
                    .placeholder(R.drawable.logo)
                    .fit()
                    .centerCrop()
                    .into(binding.ivLogo)
            }
        })
    }

    private fun configureViewModel() {
        detailsViewModel = ViewModelProvider(this, detailViewModelFactory)[DetailsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ScreenDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        observeViewState()
        detailsViewModel.setId(arguments?.getInt(REPOSITORY_ID) ?: 0)
    }

    private fun init() {
        binding.ivBackButton.setOnClickListener { activity?.onBackPressed() }
        binding.rvContributors.adapter = contributorsAdapter
        binding.buttonRetry.setOnClickListener {
            detailsViewModel.refresh()
        }
    }

    private val onItemSelectListener : OnItemSelectListener = object : OnItemSelectListener {
        override fun onSelect(owner: Owner) {
            val bundle = Bundle()
            bundle.putInt(OWNER_ID,owner.id ?: 0)
            bundle.putString(OWNER_NAME,owner.login ?: "")
            bundle.putString(OWNER_REPO_URL,owner.reposUrl ?: "")
            bundle.putString(OWNER_AVATAR,owner.avatarUrl ?: "")
            findNavController().navigate(R.id.action_detailsScreen_to_ownerScreen,bundle)
        }
    }

}