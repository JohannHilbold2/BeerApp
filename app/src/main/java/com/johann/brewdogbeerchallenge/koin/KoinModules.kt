package com.johann.brewdogbeerchallenge.koin

import androidx.fragment.app.FragmentManager
import com.johann.beers.repositories.beerlist.BeerRepository
import com.johann.beers.repositories.beerlist.BeersApiService
import com.johann.brewdogbeerchallenge.NavigationServiceImpl
import com.johann.brewdogbeerchallenge.koin.feature.beerlist.KoinBeerListFeature
import com.johann.brewdogbeerchallenge.koin.repositories.KoinRepositoryModule
import com.johann.brewdogbeerchallenge.koin.retrofit.ApiServiceImpl
import com.johann.navigation.NavigationService
import okhttp3.OkHttpClient
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object KoinModules {
    fun createAllModules(): List<Module> = listOf(module {
        single { ApiServiceImpl() }
        single { provideBeerApi(get()) }
        single { BeerRepository((get() as ApiServiceImpl).createService(), get()) }
        single { (fm: FragmentManager) ->
            provideNavigationService(fm)
        }
    })
        .union(KoinRepositoryModule.createModules())
        .union(KoinBeerListFeature.createModules())
        .toList()


    private fun provideBeerApi(service: ApiServiceImpl): BeersApiService = service.createService()
    private fun provideNavigationService(fragmentManager: FragmentManager): NavigationService =
        NavigationServiceImpl(fragmentManager)

}