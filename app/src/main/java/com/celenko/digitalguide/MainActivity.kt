package com.celenko.digitalguide

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.celenko.digitalguide.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        binding.navigationBar.setOnItemSelectedListener {
            return@setOnItemSelectedListener if (it.itemId == R.id.categoryFragment) {
                navController.navigate(R.id.categoryFragment)
                true
            } else {
                navController.navigate(R.id.favoritesFragment)
                true
            }
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->

            when (destination.id) {
                R.id.favoritesFragment,
                R.id.detailFragment,
                R.id.homeFragment,
                R.id.categoryFragment
                -> {
                    binding.navigationBar.visibility = View.VISIBLE
                }

                else -> {
                    binding.navigationBar.visibility = View.GONE
                }
            }
        }
    }
}