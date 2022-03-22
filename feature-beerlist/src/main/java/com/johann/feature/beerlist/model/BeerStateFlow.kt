package com.johann.feature.beerlist.model

import com.johann.navigation.BeerUI

class BeerStateFlow(val status: BeerState, val list: List<BeerUI>)
enum class BeerState{ LOADING, ERROR, DONE, NOMOREBEER}