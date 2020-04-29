package com.yasin.trendingrepos.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import com.yasin.trendingrepos.databinding.ScreenHomeBinding
import com.yasin.trendingrepos.getAppComponent
import com.yasin.trendingrepos.ui.ReposViewModel
import com.yasin.trendingrepos.ui.ReposViewModelFactory
import javax.inject.Inject

/**
 * Created by Yasin on 28/4/20.
 */
class HomeScreen : Fragment() {

    @Inject lateinit var reposViewModelFactory: ReposViewModelFactory
    @Inject lateinit var picasso: Picasso
    private lateinit var reposViewModel: ReposViewModel
    private lateinit var binding : ScreenHomeBinding
    private val reposAdapter : ReposAdapter by lazy {
        ReposAdapter(picasso)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getAppComponent(requireContext()).injectHomeScreen(this)
        configureViewModel()
        observeViewState()
    }

    private fun observeViewState() {
        reposViewModel.reposUi.observe(this, Observer {
            when(it) {
                is HomeViewState.Loading -> {
                    //show loading state
                }
                is HomeViewState.Error -> {
                    //show error state
                }
                is HomeViewState.Success -> {
                    reposAdapter.submitList(it.repos)
                }
            }
        })
    }

    private fun configureViewModel() {
        reposViewModel = ViewModelProvider(this,reposViewModelFactory)[ReposViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ScreenHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvRepos.adapter = reposAdapter
        reposViewModel.searchRepository("Kotlin")
    }
}