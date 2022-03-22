package com.johann.brewdogbeerchallenge

import android.app.Application
import com.johann.brewdogbeerchallenge.koin.KoinModules
import com.johann.brewdogbeerchallenge.koin.repositories.KoinRepositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.KoinExperimentalAPI
import org.koin.core.context.GlobalContext.startKoin

@KoinExperimentalAPI
class BeerApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BeerApplication)
            fragmentFactory()
            modules(KoinModules.createAllModules())
        }
    }
}