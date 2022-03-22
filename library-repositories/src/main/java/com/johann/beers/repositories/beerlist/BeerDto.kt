package com.johann.beers.repositories.beerlist

import com.squareup.moshi.Json

/**
 * "id": 1,
"name": "Buzz",
"tagline": "A Real Bitter Experience.",
"first_brewed": "09/2007",
"description": "A light, crisp and bitter IPA brewed with English and American hops. A small batch brewed only once.",
"image_url": "https://images.punkapi.com/v2/keg.png",
"abv": 4.5,
"ibu": 60,
"target_fg": 1010,
"target_og": 1044,
"ebc": 20,
"srm": 10,
"ph": 4.4,
"attenuation_level": 75,
"volume": {
"value": 20,
"unit": "litres"
},
"boil_volume": {
"value": 25,
"unit": "litres"
},
"method": {
"mash_temp": [
{
"temp": {
"value": 64,
"unit": "celsius"
},
"duration": 75
}
],
"fermentation": {
"temp": {
"value": 19,
"unit": "celsius"
}
},
"twist": null
},
"ingredients": {
"malt": [
{
"name": "Maris Otter Extra Pale",
"amount": {
"value": 3.3,
"unit": "kilograms"
}
},
{
"name": "Caramalt",
"amount": {
"value": 0.2,
"unit": "kilograms"
}
},
{
"name": "Munich",
"amount": {
"value": 0.4,
"unit": "kilograms"
}
}
],
"hops": [
{
"name": "Fuggles",
"amount": {
"value": 25,
"unit": "grams"
},
"add": "start",
"attribute": "bitter"
},
{
"name": "First Gold",
"amount": {
"value": 25,
"unit": "grams"
},
"add": "start",
"attribute": "bitter"
},
{
"name": "Fuggles",
"amount": {
"value": 37.5,
"unit": "grams"
},
"add": "middle",
"attribute": "flavour"
},
{
"name": "First Gold",
"amount": {
"value": 37.5,
"unit": "grams"
},
"add": "middle",
"attribute": "flavour"
},
{
"name": "Cascade",
"amount": {
"value": 37.5,
"unit": "grams"
},
"add": "end",
"attribute": "flavour"
}
],
"yeast": "Wyeast 1056 - American Ale™"
},
"food_pairing": [
"Spicy chicken tikka masala",
"Grilled chicken quesadilla",
"Caramel toffee cake"
],
"brewers_tips": "The earthy and floral aromas from the hops can be overpowering. Drop a little Cascade in at the end of the boil to lift the profile with a bit of citrus.",
"contributed_by": "Sam Mason <samjbmason>"
 */
data class BeerDto(
    val id: Int,
    val name: String,
    @Json(name="tagline") val tagLine: String,
    @Json(name="first_brewed")val firstBrewed: String,
    val description: String,
    @Json(name="image_url") val imageUrl: String?,
    val abv: Float,
    val ibu: Float?,
    @Json(name="target_fg") val targetFg: Int?,
    @Json(name="target_og") val targetOg: Float?,
    val ebc: Float?,
    val srm: Float?,
    val ph: Float?,
    @Json(name="attenuation_level") val attenuationLevel: Float?,
    val volume: Volume,
    @Json(name="boil_volume") val boilVolume: BoilVolume,
    val method: Method,
    val ingredients: Ingredients,
    @Json(name="food_pairing") val foodPairing: List<String>,
    @Json(name="brewers_tips") val brewerTips: String,
    @Json(name="contributed_by") val contributedBy: String
)
data class Method(@Json(name="mash_temp") val mashTemp: List<MashTemp>, val fermentation: MethodTemperature, val twist: String?)
data class MethodTemperature(val temp: Temperature)
data class MashTemp(val temp: Temperature, val duration: Int?)
data class Temperature(val value: Int?, val unit: String)
data class Volume(val value: Int, val unit: String)
data class BoilVolume(val value: Int, val unit: String)
data class Ingredients(val malt: List<Malt>, val hops: List<Hops>, val yeast: String?)
data class Hops(val name: String, val amount: HopsAmount, val add: String, val attribute: String)
data class Malt(val name: String, val amount: MaltAmount)
data class MaltAmount(val value: String, val unit: String)
data class HopsAmount(val value: Float, val unit: String)