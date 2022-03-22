package com.johann.feature.beerlist.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johann.beers.repositories.DataResult
import com.johann.beers.repositories.beerlist.BeerRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BeerListViewModel(private val beerRepository: BeerRepository) : ViewModel() {
    var savedIndex = 0
    var savedOffset = 0
    lateinit var job : Job

    private val mutableBeersStateFlow =
        MutableStateFlow(BeerStateFlow(BeerState.LOADING, emptyList()))
    val beersStateFlow: StateFlow<BeerStateFlow> = mutableBeersStateFlow

    fun getBeers(page: Int) {
        if(!::job.isInitialized || !job.isActive) {
            job = viewModelScope.async {
                val previousBeers = beersStateFlow.value.list
                mutableBeersStateFlow.tryEmit(BeerStateFlow(BeerState.LOADING, previousBeers))
                when (val result = beerRepository.getBeers(page)) {
                    is DataResult.Success -> {
                        val mappedBeers = result.data.map {
                            BeerUiMapper.mapDTO(it)
                        }
                        if (mappedBeers.isNotEmpty()) {
                            mutableBeersStateFlow.tryEmit(
                                BeerStateFlow(
                                    BeerState.DONE,
                                    previousBeers + mappedBeers
                                )
                            )
                        } else {
                            mutableBeersStateFlow.tryEmit(
                                BeerStateFlow(
                                    BeerState.NOMOREBEER,
                                    previousBeers
                                )
                            )
                        }

                    }
                    else -> {
                        mutableBeersStateFlow.tryEmit(BeerStateFlow(BeerState.ERROR, emptyList()))
                    }
                }
            }
        }
    }

    fun getBeerPageSize(): Int = 25
    fun clearAnySavedBeer() {
        savedIndex = 0
        savedOffset = 0
        mutableBeersStateFlow.tryEmit(BeerStateFlow(BeerState.DONE, emptyList()))
    }

    fun saveScrollPosition(firstVisibleItemIndex: Int, firstVisibleItemScrollOffset: Int) {
        this.savedIndex = firstVisibleItemIndex
        this.savedOffset = firstVisibleItemScrollOffset
    }
}