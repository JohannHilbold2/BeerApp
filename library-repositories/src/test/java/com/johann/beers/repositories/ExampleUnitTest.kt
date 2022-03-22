package com.johann.beers.repositories

import com.johann.beers.repositories.beerlist.BeerDto
import com.johann.beers.repositories.beerlist.BeerRepository
import com.johann.beers.repositories.beerlist.BeersApiService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get

class ExampleUnitTest : KoinTest {
    private fun provideBeerApi(service: TestApiServiceImpl): BeersApiService = service.createTestService()
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `parsing json works`() {
        val mockServer = BeerServer()
        startKoin{
            modules(
                module {
                    single { TestApiServiceImpl(mockServer.getBaseUrl()) }
                    single { provideBeerApi(get()) }
                    factory { RequestHelper(TestConnectionService()) }
                    single { BeerRepository(get(),get()) }
                })
        }
        val repo : BeerRepository = get()

        runTest {
           val beers = repo.getBeers(1) as DataResult.Success<List<BeerDto>>

            Assert.assertEquals(beers.data[0].name, "Buzz")
            Assert.assertNotEquals(beers.data[0].name, "Buzz2")
        }
    }


}