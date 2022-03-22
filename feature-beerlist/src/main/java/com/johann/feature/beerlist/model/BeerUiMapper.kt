package com.johann.feature.beerlist.model

import com.johann.beers.repositories.beerlist.*
import com.johann.navigation.*

object BeerUiMapper {
    fun mapDTO(dto: BeerDto) = BeerUI(dto.name, dto.imageUrl?:"", dto.abv, mapHops(dto.ingredients.hops), mapMalt(dto.ingredients.malt), mapMethod(dto.method))
    private fun mapHops(hops: List<Hops>) = hops.map {
        HopsUI(it.name, HopsAmountUI(it.amount.value, it.amount.unit), it.add, it.attribute)
    }
    private fun mapMalt(malts: List<Malt>) = malts.map {
        MaltUI(it.name, MaltAmountUI(it.amount.value, it.amount.unit))
    }
    private fun mapMethod(method: Method) = MethodUI(mapMethodmash(method.mashTemp), mapMethodFermentation(method.fermentation), method.twist)

    private fun mapMethodFermentation(fermentation: MethodTemperature): MethodTemperatureUI = MethodTemperatureUI(TemperatureUI(fermentation.temp.value, fermentation.temp.unit))

    private fun mapMethodmash(mashTemp: List<MashTemp>): List<MashTempUI> = mashTemp.map {
        MashTempUI(TemperatureUI(it.temp.value, it.temp.unit), it.duration)
    }
}