package com.dlutrix.themoviewikicompose.ui.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Overview(
    overview: String
) {
    Column(
        modifier = Modifier.padding(
            start = 16.dp,
            end = 16.dp,
        )
    ) {
        Text(
            text = "Overview",
            modifier = Modifier.padding(top = 16.dp),
            fontSize = 14.sp,
            fontWeight = FontWeight.W600
        )
        Text(
            text = overview,
            modifier = Modifier.padding(top = 8.dp),
            fontSize = 14.sp,
            lineHeight = 25.sp
        )
    }
}