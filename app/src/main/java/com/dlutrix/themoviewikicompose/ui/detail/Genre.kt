package com.dlutrix.themoviewikicompose.ui.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dlutrix.themoviewikicompose.data.model.Genre

@Composable
fun Genre(
    genres: List<Genre>
) {

    val genre = genres.joinToString {
        "${it.name}"
    }

    Column(
        modifier = Modifier.padding(
            start = 16.dp,
            end = 16.dp,
            bottom = 16.dp
        )
    ) {
        Text(
            text = "Genres",
            modifier = Modifier.padding(top = 16.dp),
            fontSize = 14.sp,
            fontWeight = FontWeight.W600,
        )
        Text(
            text = genre,
            modifier = Modifier.padding(top = 8.dp),
            fontSize = 14.sp,
            lineHeight = 25.sp
        )
    }
}