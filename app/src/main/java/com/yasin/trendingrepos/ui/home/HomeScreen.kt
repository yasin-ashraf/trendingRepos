package com.yasin.trendingrepos.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
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
                    binding.swipeRefresh.isRefreshing = false
                    binding.viewFlipper.displayedChild = 0
                }
                is HomeViewState.Error -> {
                    binding.swipeRefresh.isRefreshing = false
                    binding.viewFlipper.displayedChild = 2
                }
                is HomeViewState.Success -> {
                    binding.swipeRefresh.isRefreshing = false
                    if(it.repos.isNotEmpty()) {
                        binding.viewFlipper.displayedChild = 1
                        reposAdapter.submitList(it.repos)
                    }
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
        init()
    }

    private fun init() {
        binding.rvRepos.adapter = reposAdapter
        binding.viewFlipper.displayedChild = 3 //empty state first
        binding.swipeRefresh.setOnRefreshListener {
            reposViewModel.forceRefresh(true)
        }
        addEditorActionListener()
    }

    private fun addEditorActionListener() {
        binding.etSearch.setOnEditorActionListener { textView, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH) {
                reposViewModel.searchRepository(textView.text.toString())
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }
}