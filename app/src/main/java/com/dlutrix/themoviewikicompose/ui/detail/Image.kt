package com.dlutrix.themoviewikicompose.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.dlutrix.themoviewikicompose.data.model.Backdrop

@ExperimentalCoilApi
@Composable
fun Image(
    images: List<Backdrop>
) {
    LazyRow(
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            top = 16.dp
        )
    ) {
        items(images) { image ->
            Card(
                modifier = Modifier
                    .height(185.dp)
                    .width(300.dp)
                    .padding(end = 16.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Image(
                    painter = rememberImagePainter(
                        "https://image.tmdb.org/t/p/w500/${image.filePath ?: ""}",
                        builder = {
                            crossfade(true)
                        }
                    ), contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}