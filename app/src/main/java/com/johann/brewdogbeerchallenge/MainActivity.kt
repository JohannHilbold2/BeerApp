package com.johann.brewdogbeerchallenge

import android.os.Bundle
import android.view.View
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.johann.brewdogbeerchallenge.databinding.ActivityMainBinding
import com.johann.navigation.BeerListNavigation
import com.johann.navigation.LoadingInActionBarActivity
import com.johann.navigation.NavigationService
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class MainActivity : AppCompatActivity(), LoadingInActionBarActivity {

    private lateinit var binding: ActivityMainBinding
    private val navigationService: NavigationService by inject {
        parametersOf(supportFragmentManager)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        navigationService.fragmentManager = supportFragmentManager
        navigationService.goTo(BeerListNavigation())
    }

    override fun showLoading(show: Boolean) {
        binding.toolbarprogress.visibility = if(show)View.VISIBLE else View.GONE
    }


}