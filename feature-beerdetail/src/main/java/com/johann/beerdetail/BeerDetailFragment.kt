package com.johann.beerdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import com.johann.navigation.BeerDetailNavigation
import com.johann.navigation.BeerUI
import com.johann.navigation.NavigableFragment
import com.johann.navigation.NavigationService
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf


class BeerDetailFragment : NavigableFragment() {

    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_beer_detail, container, false)
        inflate.findViewById<ComposeView>(R.id.compose_view).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                if(requireArguments().getSerializable("beer") is BeerUI){
                    val beerUI = requireArguments().getSerializable("beer") as BeerUI
                    activity?.actionBar?.title = beerUI.name
                    BeerDetail(beerUI)
                }
            }
        }
        return inflate
    }

    companion object {
        fun createFragment(bundle: Bundle?): BeerDetailFragment {
            val f = BeerDetailFragment()
            f.arguments = bundle
            return f
        }

    }
}