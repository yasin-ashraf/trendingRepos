package com.yasin.trendingrepos.ui.contributor

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
import com.yasin.trendingrepos.databinding.ScreenOwnerBinding
import com.yasin.trendingrepos.getAppComponent
import com.yasin.trendingrepos.network.NetworkState
import com.yasin.trendingrepos.ui.home.OnItemSelectListener
import com.yasin.trendingrepos.ui.home.ReposAdapter
import com.yasin.trendingrepos.ui.uiDataModel.RepositoryUi
import com.yasin.trendingrepos.utils.*
import javax.inject.Inject

/**
 * Created by Yasin on 29/4/20.
 */
class ContributorScreen : Fragment() {

    @Inject lateinit var contributorViewModelFactory: ContributorViewModelFactory
    @Inject lateinit var picasso: Picasso
    private lateinit var binding : ScreenOwnerBinding
    private lateinit var contributorViewModel: ContributorViewModel
    private val reposAdapter : ReposAdapter by lazy {
        ReposAdapter(picasso,onItemSelectListener)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getAppComponent(requireContext()).injectContributorScreen(this)
        configureViewModel()
    }

    private fun configureViewModel() {
        contributorViewModel = ViewModelProvider(this, contributorViewModelFactory)[ContributorViewModel::class.java]
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ScreenOwnerBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        observeRepos()
        contributorViewModel.setUrl(arguments?.getString(OWNER_REPO_URL) ?: "")
    }

    private fun observeRepos() {
        contributorViewModel.repos.observe(this.viewLifecycleOwner, Observer {
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
                    val list = mutableListOf<RepositoryUi>()
                    it.data?.forEach { repo ->
                        list.add(repo.convertToUi())
                    }
                    reposAdapter.submitList(list)
                }
            }
        })
    }

    private fun init() {
        binding.tvLoginName.text = arguments?.getString(OWNER_NAME) ?: ""
        binding.title.text = arguments?.getString(OWNER_NAME) ?: ""
        picasso.load(arguments?.getString(OWNER_AVATAR) ?: "")
            .placeholder(R.drawable.logo)
            .fit()
            .centerCrop()
            .into(binding.ivLogo)

        binding.ivBackButton.setOnClickListener { activity?.onBackPressed() }
        binding.rvRepos.adapter = reposAdapter
        binding.buttonRetry.setOnClickListener {
            contributorViewModel.refresh()
        }
    }

    private val onItemSelectListener : OnItemSelectListener = object : OnItemSelectListener {
        override fun onSelect(id: Int) {
            val bundle = Bundle()
            bundle.putInt(REPOSITORY_ID,id)
            findNavController().navigate(R.id.action_ownerScreen_to_detailsScreen,bundle)
        }
    }


}