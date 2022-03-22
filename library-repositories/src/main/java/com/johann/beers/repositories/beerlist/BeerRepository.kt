package com.johann.beers.repositories.beerlist

import com.johann.beers.repositories.DataResult
import com.johann.beers.repositories.RequestHelper

class BeerRepository(
    private val api: BeersApiService,
    private val requestHelper: RequestHelper
) {
    suspend fun getBeers(page: Int): DataResult<out List<BeerDto>> = requestHelper.tryRequest { api.getBeers(page) }
}