package com.johann.navigation

import android.os.Bundle
import androidx.fragment.app.FragmentManager

interface NavigationService {
    var fragmentManager: FragmentManager
    fun goTo(navigation: Navigation)
}
sealed class Navigation(val bundle: Bundle?)
class BeerDetailNavigation(b: Bundle?): Navigation(b)
class BeerListNavigation(): Navigation(null)