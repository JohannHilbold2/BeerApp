package com.johann.feature.beerlist.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImage
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.ImageRequest
import com.johann.feature.beerlist.R
import com.johann.feature.beerlist.model.BeerListViewModel
import com.johann.feature.beerlist.model.BeerState
import com.johann.feature.beerlist.model.BeerStateFlow
import com.johann.navigation.BeerUI
import com.johann.navigation.MethodTemperatureUI
import com.johann.navigation.MethodUI
import com.johann.navigation.TemperatureUI
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun BeerList(viewModel: BeerListViewModel, onBeerClicked: (BeerUI) -> Unit) {
    val beerState = viewModel.beersStateFlow.collectAsState()
    val savedIndex = viewModel.savedIndex
    val savedOffset = viewModel.savedOffset
    val listState = rememberLazyListState(savedIndex, savedOffset)
    when (beerState.value.status) {
        BeerState.ERROR -> ShowErrorComposable(viewModel)
        else -> {
            ShowActualBeerList(
                savedIndex,
                savedOffset,
                listState,
                beerState,
                viewModel,
                onBeerClicked
            )

            if(beerState.value.status == BeerState.LOADING){
                ShowLoadingComposable()
            }
        }
    }

}

@ExperimentalMaterialApi
@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ShowActualBeerList(
    savedIndex: Int,
    savedOffset: Int,
    listState: LazyListState,
    beerState: State<BeerStateFlow>,
    viewModel: BeerListViewModel,
    onBeerClicked: (BeerUI) -> Unit
) {
    ScrollToItemIfNeeded(savedIndex, savedOffset, listState)

    LazyVerticalGrid(
        cells = GridCells.Adaptive(minSize = 150.dp),
        state = listState
    ) {
        items(beerState.value.list.size) { b ->
            //loading next page when displaying the "size -2" item
            if (b == beerState.value.list.size - 2 && beerState.value.status != BeerState.NOMOREBEER && beerState.value.status != BeerState.LOADING) {
                viewModel.saveScrollPosition(
                    listState.firstVisibleItemIndex,
                    listState.firstVisibleItemScrollOffset
                )
                viewModel.getBeers(beerState.value.list.size / viewModel.getBeerPageSize() + 1)
            }
            ShowOneBeer(beerState.value.list[b], onBeerClicked = onBeerClicked)
        }
    }
}

@Composable
private fun ScrollToItemIfNeeded(
    savedIndex: Int,
    savedOffset: Int,
    listState: LazyListState
) {
    if (savedIndex != 0 || savedOffset != 0) {
        val coroutineScope = rememberCoroutineScope()
        LaunchedEffect(key1 = "", block = {
            coroutineScope.launch {
                listState.scrollToItem(savedIndex, savedOffset)
            }
        })
    }
}

@Preview
@Composable
fun ShowLoadingComposable() {
    Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Center) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(30.dp)
                .align(Alignment.Bottom)
        )
    }
}

@Composable
fun ShowErrorComposable(viewModel: BeerListViewModel) {
    Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Center) {
        Text(
            text = "Error :(",
            modifier = Modifier
                .align(Alignment.CenterVertically)
        )
        Button(modifier = Modifier
            .align(Alignment.CenterVertically),
            onClick = {
                viewModel.clearAnySavedBeer()
                viewModel.getBeers(1)
            }) {
            Text(text = "Retry")
        }
    }
}


@OptIn(ExperimentalCoilApi::class)
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun ShowOneBeer(beer: BeerUI, onBeerClicked: (BeerUI) -> Unit) {
    Card(modifier = Modifier.padding(5.dp), onClick = { onBeerClicked.invoke(beer) }) {
        Row(modifier = Modifier.padding(5.dp)) {
            OutlinedButton(modifier = Modifier
                .size(48.dp),
                shape = CircleShape,
                border = BorderStroke(1.dp, Color.LightGray),
                contentPadding = PaddingValues(0.dp),
                onClick = {}) {
                ShowImage(beer.imageURL)
            }
            Text(
                beer.name, modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.7f)
                    .align(Alignment.CenterVertically)
                    .padding(start = 5.dp)
            )
            Text(
                beer.abv.toString() + "°", modifier = Modifier
                    .fillMaxHeight()
                    .align(Alignment.CenterVertically)
            )
        }
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
        contentDescription = stringResource(R.string.description),
        contentScale = ContentScale.Crop,
        modifier = Modifier.clip(CircleShape)
    )
}

@ExperimentalMaterialApi
@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun OneBeer() {
    val beer = BeerUI(
        "Buzz", "https://images.punkapi.com/v2/keg.png", 4.5f, emptyList(), emptyList(), MethodUI(
            emptyList(), MethodTemperatureUI(TemperatureUI(43, "celsius")), "twist"
        )
    )
    ShowOneBeer(beer, {})
}