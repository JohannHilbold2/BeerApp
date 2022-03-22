package com.johann.beerdetail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImage
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.ImageRequest
import com.johann.navigation.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview
fun PreviewBuzzBeer() {
    BeerDetail(
        beerUI = BeerUI(
            "Buzz",
            abv = 4.5f,
            hops = listOf(
                HopsUI(
                    "Fuggles",
                    HopsAmountUI(25f, "grams"),
                    "start",
                    attribute = "bitter"
                )
            ),
            imageURL = "https://images.punkapi.com/v2/keg.png",
            malt = listOf(MaltUI("Maris Otter Extra Pale", MaltAmountUI("3.3", "kilogram"))),
            method = MethodUI(
                listOf(MashTempUI(TemperatureUI(64, "celsius"), 75)), MethodTemperatureUI(
                    TemperatureUI(24, "celsius")
                ), null
            ),
        )
    )
}

@OptIn(ExperimentalCoilApi::class)
@ExperimentalFoundationApi
@Composable
fun BeerDetail(beerUI: BeerUI) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            ShowImage(url = beerUI.imageURL)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = beerUI.name,
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    textAlign = TextAlign.Center
                )
                Text(text = beerUI.abv.toString() + "°",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center)
            }

        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            item {
                Text(
                    text = "Hops",
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    textAlign = TextAlign.Center
                )
            }
            items(beerUI.hops.size) { b ->
                ShowHops(beerUI.hops[b])
            }

            item {
                Text(
                    text = "Malts",
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    textAlign = TextAlign.Center
                )
            }
            items(beerUI.malt.size) { b ->
                ShowMalts(beerUI.malt[b])
            }
            item {
                Text(
                    text = "Method",
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    textAlign = TextAlign.Center
                )
            }
            item {
                beerUI.method.twist?.let {
                    Text(text = it,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center)
                }
            }
            item {
                Text(
                    text = "Fermentation",
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
            }
            item {
                Text(text = beerUI.method.fermentation.temp.value.toString() + " " + beerUI.method.fermentation.temp.unit,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth())
            }
            item {
                Text(
                    text = "Mash Temp",
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
            }
            items(beerUI.method.mashTemp.size) { b ->
                Text(text = beerUI.method.mashTemp[b].temp.value.toString() + " " + beerUI.method.mashTemp[b].temp.unit,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth())
            }
        }
    }
}

@Composable
fun ShowMalts(maltUI: MaltUI) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            Text(
                text = maltUI.name,
                fontWeight = FontWeight.Bold
            )
        }
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            Text(text = maltUI.amount.value + " " + maltUI.amount.unit)
        }
    }
}

@Composable
fun ShowHops(hopsUI: HopsUI) {
    Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
        Text(text = hopsUI.name, fontWeight = FontWeight.Bold)
        Text(text = " (" + hopsUI.attribute + ")")
    }
    Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
        Text(text = hopsUI.add, fontWeight = FontWeight.Bold)
        Text(text = " (" + hopsUI.amount.value.toString() + " " + hopsUI.amount.unit + ")")
    }
}

@ExperimentalCoilApi
@Composable
fun ShowImage(url: String?) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .crossfade(true)

            .build(),
        imageLoader = ImageLoader.Builder(LocalContext.current)
            .memoryCache(
                MemoryCache.Builder(LocalContext.current)
                    .maxSizePercent(0.25)
                    .build()
            )
            .diskCache(
                DiskCache.Builder()
                    .directory(LocalContext.current.cacheDir.resolve("image_cache"))
                    .maxSizePercent(0.02)
                    .build()
            )
            .build(),
        placeholder = painterResource(R.drawable.ic_placeholder),
        contentDescription = "don't look here",
        contentScale = ContentScale.Crop
    )
}