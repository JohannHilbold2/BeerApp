package com.johann.brewdogbeerchallenge

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.johann.beerdetail.BeerDetailFragment
import com.johann.feature.beerlist.ui.BeerListFragment
import com.johann.navigation.BeerDetailNavigation
import com.johann.navigation.BeerListNavigation
import com.johann.navigation.Navigation
import com.johann.navigation.NavigationService

class NavigationServiceImpl(override var fragmentManager: FragmentManager) : NavigationService {

    override fun goTo(navigation: Navigation) {
        when(navigation){
            is BeerDetailNavigation -> fragmentManager.commit {
                replace(R.id.fragmentMenuMainContainer, BeerDetailFragment.createFragment(navigation.bundle), "fragmentTag").addToBackStack("detail")
            }
            is BeerListNavigation -> fragmentManager.commit {
                replace(R.id.fragmentMenuMainContainer, BeerListFragment.createFragment(navigation.bundle), "fragmentTag")
            }
        }
    }
}