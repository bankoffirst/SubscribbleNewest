package com.example.subscribble.activities

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {

    val total = 0f
    val formattedTotal = String.format("%.2f", total)
    val videoPrice = 0f
    val formattedvideoPrice = String.format("%.2f", videoPrice)
    val musicPrice = 0f
    val formattedmusicPrice = String.format("%.2f", musicPrice)

    Scaffold(
        topBar = {
                Text(
                    text = "Home",
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    modifier = Modifier.padding(start = 26.dp, top = 22.dp, bottom = 22.dp)
                )
        }
    ) { contentPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp)
                    .height(220.dp),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF29292B))
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(top = 22.dp, start = 26.dp, end = 26.dp).weight(1f)
                ) {
                    Text(
                        text = "Total Price",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = Color.White
                    )
                    Text(
                        text = "$formattedTotal THB",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = Color(0xFF0AA6EC),
                        textAlign = TextAlign.Right,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.5f)
                )

                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(top = 22.dp, start = 26.dp, end = 26.dp).weight(1f)
                ) {
                    Text(
                        text = "Video Streaming",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color(0xFF808080)
                    )
                    Text(
                        text = "$formattedvideoPrice THB",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color(0xFF808080),
                        textAlign = TextAlign.Right,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(start = 26.dp, end = 26.dp).weight(1f)
                ) {
                    Text(
                        text = "Music Streaming",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color(0xFF808080)
                    )
                    Text(
                        text = "$formattedmusicPrice THB",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color(0xFF808080),
                        textAlign = TextAlign.Right,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Text(
                text = "Your subscription",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(start = 26.dp, top = 28.dp)
            )

            //Subscriptions

        }
    }
}


@Preview(showBackground = true, widthDp = 480, heightDp = 1030)
@Composable
fun HomeScreenPreview(){
    HomeScreen()
}