package com.dlutrix.themoviewikicompose.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.dlutrix.themoviewikicompose.R
import com.dlutrix.themoviewikicompose.ui.theme.Maroon
import com.dlutrix.themoviewikicompose.ui.theme.Teal
import com.dlutrix.themoviewikicompose.ui.theme.TexasRose

@ExperimentalCoilApi
@Composable
fun Poster(
    imageUrl: String,
    screenHeight: Int,
    runtime: Int,
    releaseDate: String,
    voteAverage: Double
) {
    Row(
        modifier = Modifier
            .height((screenHeight * 0.5f).dp)
            .padding(
                bottom = 16.dp, start = 16.dp,
                end = 16.dp,
            ),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .height((screenHeight * 0.65f).dp)
                .weight(2f)
                .padding(end = 8.dp),
            backgroundColor = Color.Transparent
        ) {
            Image(
                painter = rememberImagePainter(imageUrl, builder = {
                    crossfade(true)
                }),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )
        }
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .weight(1f)
        ) {
            Card(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(16.dp),
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(8.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_round_star_24),
                        contentDescription = "Rating",
                        tint = TexasRose,
                        modifier = Modifier.weight(2f)
                    )
                    Text(
                        text = "Rating",
                        fontWeight = FontWeight.W300,
                        fontSize = 12.sp,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "$voteAverage/10",
                        fontWeight = FontWeight.W600,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            Card(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(16.dp),
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(8.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_round_access_time_filled_24),
                        contentDescription = "Rating",
                        tint = Teal,
                        modifier = Modifier.weight(2f)
                    )
                    Text(
                        text = "Duration",
                        fontWeight = FontWeight.W300,
                        fontSize = 12.sp,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = runtime.toString() + "min",
                        fontWeight = FontWeight.W600,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            Card(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                shape = RoundedCornerShape(16.dp),
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(8.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_round_date_range_24),
                        contentDescription = "Rating",
                        tint = Maroon,
                        modifier = Modifier.weight(2f)
                    )
                    Text(
                        text = "Release Date",
                        fontWeight = FontWeight.W300,
                        fontSize = 12.sp,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = releaseDate,
                        fontWeight = FontWeight.W600,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}