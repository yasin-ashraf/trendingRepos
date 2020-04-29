package com.yasin.trendingrepos.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import com.yasin.trendingrepos.R
import com.yasin.trendingrepos.databinding.ScreenHomeBinding
import com.yasin.trendingrepos.getAppComponent
import com.yasin.trendingrepos.ui.ReposViewModel
import com.yasin.trendingrepos.ui.ReposViewModelFactory
import com.yasin.trendingrepos.utils.REPOSITORY_ID
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
        ReposAdapter(picasso,onItemSelectListener)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getAppComponent(requireContext()).injectHomeScreen(this)
        configureViewModel()
    }

    private fun observeViewState() {
        reposViewModel.reposUi.observe(this.viewLifecycleOwner, Observer {
            when(it) {
                is HomeViewState.Loading -> {
                    binding.swipeRefresh.isRefreshing = true
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
                    }else {
                        binding.viewFlipper.displayedChild = 0
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
        binding = ScreenHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        observeViewState()
    }

    private fun init() {
        binding.rvRepos.adapter = reposAdapter
        binding.swipeRefresh.setOnRefreshListener {
            reposViewModel.forceRefresh(true)
        }
        addEditorActionListener()
        reposViewModel.searchRepository("Kotlin")
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

    private val onItemSelectListener : OnItemSelectListener = object : OnItemSelectListener {
        override fun onSelect(id: Int) {
            val bundle = Bundle()
            bundle.putInt(REPOSITORY_ID,id)
            findNavController().navigate(R.id.action_homeScreen_to_detailsScreen,bundle)
        }
    }
}