package com.johann.beers.repositories.beerlist
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface BeersApiService {
    @Headers("Accept: application/json; ")
    @GET("/v2/beers")
    suspend fun getBeers(@Query("page") page: Int): List<BeerDto>
}