package com.johann.brewdogbeerchallenge.koin.repositories

import com.johann.beers.repositories.ConnectionService
import com.johann.beers.repositories.ConnectionServiceImpl
import com.johann.beers.repositories.RequestHelper
import com.johann.beers.repositories.beerlist.BeerRepository
import org.koin.core.module.Module
import org.koin.dsl.module

object KoinRepositoryModule {
    fun createModules(): List<Module> = listOf(module {
        factory<ConnectionService> { ConnectionServiceImpl(get()) }
        factory { RequestHelper(get()) }
        factory { BeerRepository(get(), get()) }
    })
}