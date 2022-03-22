package com.johann.navigation

import java.io.Serializable

data class BeerUI(
    val name: String,
    val imageURL: String,
    val abv: Float,
    val hops: List<HopsUI>,
    val malt: List<MaltUI>,
    val method: MethodUI
) : Serializable{
}
data class HopsUI(val name: String, val amount: HopsAmountUI, val add: String, val attribute: String)
data class MaltUI(val name: String, val amount: MaltAmountUI)
data class MaltAmountUI(val value: String, val unit: String)
data class HopsAmountUI(val value: Float, val unit: String)
data class MethodUI(val mashTemp: List<MashTempUI>, val fermentation: MethodTemperatureUI, val twist: String?)
data class MethodTemperatureUI(val temp: TemperatureUI)
data class MashTempUI(val temp: TemperatureUI, val duration: Int?)
data class TemperatureUI(val value: Int?, val unit: String)