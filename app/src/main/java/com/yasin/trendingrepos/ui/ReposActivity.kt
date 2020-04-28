package com.yasin.trendingrepos.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.yasin.trendingrepos.R
import com.yasin.trendingrepos.databinding.ActivityReposBinding

class ReposActivity : AppCompatActivity() {

  private lateinit var binding: ActivityReposBinding
  private lateinit var navController: NavController

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityReposBinding.inflate(layoutInflater)
    setContentView(binding.root)
    init()
  }

  private fun init() {
    val navHostFragment: NavHostFragment =
      supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
    navController = navHostFragment.navController
    NavigationUI.setupActionBarWithNavController(this,navController)
  }
}
