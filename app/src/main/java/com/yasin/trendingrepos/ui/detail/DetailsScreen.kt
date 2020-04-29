package com.yasin.trendingrepos.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import com.yasin.trendingrepos.R
import com.yasin.trendingrepos.databinding.ScreenDetailsBinding
import com.yasin.trendingrepos.getAppComponent
import com.yasin.trendingrepos.utils.REPOSITORY_ID
import com.yasin.trendingrepos.utils.dateToFormat
import javax.inject.Inject

/**
 * Created by Yasin on 29/4/20.
 */
class DetailsScreen : Fragment() {

    @Inject lateinit var detailViewModelFactory: DetailViewModelFactory
    @Inject lateinit var picasso: Picasso
    private lateinit var detailsViewModel: DetailsViewModel
    private lateinit var binding : ScreenDetailsBinding

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

    }

    private fun observeDetails() {
        detailsViewModel.repoDetailsDb.observe(this.viewLifecycleOwner, Observer { repo ->
            if(repo != null) {
                binding.tvRepoName.text = repo.fullName
                binding.tvRepoUpdatedAt.text = repo.pushedAt.dateToFormat()
                binding.tvRepoLanguage.text = repo.language
                binding.tvRepoDescription.text = repo.description
                binding.tvRepoWatchers.text = repo.watchersCount.toString()
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
        binding.ivBackButton.setOnClickListener { findNavController().navigateUp() }
        Log.d(REPOSITORY_ID,(arguments?.getInt(REPOSITORY_ID) ?: 0).toString())
    }
}