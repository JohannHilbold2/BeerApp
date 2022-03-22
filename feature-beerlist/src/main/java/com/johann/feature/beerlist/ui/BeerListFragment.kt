package com.johann.feature.beerlist.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.johann.feature.beerlist.R
import com.johann.feature.beerlist.model.BeerListViewModel
import com.johann.feature.beerlist.model.BeerState
import com.johann.navigation.BeerDetailNavigation
import com.johann.navigation.LoadingInActionBarActivity
import com.johann.navigation.NavigableFragment
import com.johann.navigation.NavigationService
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.observeOn
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class BeerListFragment : NavigableFragment() {
    private val viewModel : BeerListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getBeers(1)
        lifecycleScope.launch {
            viewModel.beersStateFlow.collect{
                if(it.status == BeerState.LOADING){
                    if(activity is LoadingInActionBarActivity) (requireActivity() as LoadingInActionBarActivity).showLoading(true)
                } else {
                    if(activity is LoadingInActionBarActivity) (requireActivity() as LoadingInActionBarActivity).showLoading(false)
                }
            }
        }
    }

    @OptIn(
        ExperimentalFoundationApi::class,
        ExperimentalMaterialApi::class
    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_beer_list, container, false)
        inflate.findViewById<ComposeView>(R.id.compose_view).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                BeerList(viewModel) {
                    val b = Bundle()
                    b.putSerializable("beer", it)
                    navigationService.goTo(BeerDetailNavigation(b))
                }
            }
        }
        return inflate
    }
    companion object {
        fun createFragment(bundle: Bundle?): BeerListFragment{
            val f = BeerListFragment()
            f.arguments = bundle
            return f
        }
    }
}