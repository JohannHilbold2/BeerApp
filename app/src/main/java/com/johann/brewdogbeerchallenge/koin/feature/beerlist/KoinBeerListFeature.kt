package com.johann.brewdogbeerchallenge.koin.feature.beerlist

import com.johann.feature.beerlist.model.BeerListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object KoinBeerListFeature {
    fun createModules(): List<Module> = listOf(module {
        viewModel { BeerListViewModel(get()) }
    })
}