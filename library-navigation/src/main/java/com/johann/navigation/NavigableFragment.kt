package com.johann.navigation

import androidx.fragment.app.Fragment
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

open class NavigableFragment : Fragment() {
    open val navigationService: NavigationService by inject {
        parametersOf(activity?.supportFragmentManager)
    }
    override fun onResume() {
        super.onResume()
        activity?.supportFragmentManager?.let {
            navigationService.fragmentManager = it
        }
    }
}