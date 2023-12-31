package com.example.subscribble.activities

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.*

import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Alignment
import com.example.subscribble.navbar.NavScreen
import androidx.navigation.NavController
import androidx.compose.ui.text.TextStyle
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.subscribble.PriceFormat
import com.example.subscribble.R
import com.example.subscribble.database.module.SubscriptionViewModel
import androidx.compose.ui.graphics.ColorFilter

@Composable
fun DoNut(
    subViewmodel: SubscriptionViewModel = hiltViewModel(),
    size: Dp = 150.dp,
    thickness: Dp = 60.dp
) {
    val sumPriceMusic = subViewmodel.sumPriceByCategory("music")
    val sumPriceVideo = subViewmodel.sumPriceByCategory("video")

    val values = mutableListOf(sumPriceMusic,sumPriceVideo)

    val sumOfValues = values.sum()
    val musicvideoPrice = String.format("%.2f", sumOfValues)

    val colors = listOf(
        Color(0xFF56bfee ),
        Color(0xFF0aa6ec )
    )
    val proportions = values.map {
        it * 100 / sumOfValues
    }

    val sweepAngles = proportions.map {
        360 * it / 100
    }

    Box(
        modifier = Modifier
            .size(size = size)
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            var startAngle = -90f

            if (sumPriceMusic == 0.0f && sumPriceVideo == 0.0f) {
                drawArc(
                    color = Color.LightGray,
                    startAngle = startAngle,
                    sweepAngle = 360f,
                    useCenter = false,
                    style = Stroke(width = thickness.toPx(), cap = StrokeCap.Butt)
                )
            } else {
                for (i in values.indices) {
                    drawArc(
                        color = colors[i],
                        startAngle = startAngle,
                        sweepAngle = sweepAngles[i],
                        useCenter = false,
                        style = Stroke(width = thickness.toPx(), cap = StrokeCap.Butt)
                    )
                    startAngle += sweepAngles[i]
                }
            }
        }

        val text = "$musicvideoPrice/month"

        Text(
            text = text,
            style = TextStyle(
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            ),
            modifier = Modifier
                .align(Alignment.Center)
                .padding(4.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataVisualizationScreen(navController: NavController, subViewmodel: SubscriptionViewModel = hiltViewModel()) {

    val sumPriceMusic = subViewmodel.sumPriceByCategory("music")
    val sumPriceVideo = subViewmodel.sumPriceByCategory("video")

    val formattedvideoPrice = String.format("%.2f", sumPriceVideo)
    val formattedmusicPrice = String.format("%.2f", sumPriceMusic)

    Scaffold(
        topBar = {
            Text(
                text = "Data Visualization",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier.padding(start = 26.dp, top = 22.dp, bottom = 22.dp),
                color = colorResource(id = R.color.custom_text)
            )
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = "Monthly costs",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, start = 30.dp, end = 30.dp)
                    .height(250.dp)
                    .shadow(elevation = 8.dp, shape = RoundedCornerShape(20.dp)),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    DoNut()
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding)
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 30.dp, end = 30.dp)
                        .height(80.dp)
                        .shadow(elevation = 8.dp, shape = RoundedCornerShape(20.dp))
                        .clickable { navController.navigate(NavScreen.VideoDonut.route) },
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Box(
                            modifier = Modifier
                                .background(
                                    if (sumPriceVideo == 0.0f){
                                        Color.LightGray
                                    } else {
                                        Color(0xFF0aa6ec)
                                    }, shape = RoundedCornerShape(16.dp) )
                                .size(50.dp),
                        ) {
                            Image(painter = painterResource(id = R.drawable.ic_video),
                                contentDescription = "video streaming",
                                modifier = Modifier
                                    .size(30.dp)
                                    .clip(RoundedCornerShape(20.dp))
                                    .align(Alignment.Center),
                                colorFilter = ColorFilter.tint(color = Color.White)
                            )
                        }
                        Column(
                            modifier = Modifier
                                .width(150.dp)
                                .padding(start = 10.dp),
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .weight(1f),
                                contentAlignment = Alignment.BottomStart,
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    Text(
                                        text = "Video Streaming",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp,
                                        color = colorResource(id = R.color.custom_text),
                                    )
                                    Spacer(modifier = Modifier.width(5.dp))
                                }
                            }
                            Text(
                                text = PriceFormat(price = formattedvideoPrice),
                                modifier = Modifier
                                    .fillMaxSize()
                                    .weight(1f),
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                        }
                    }
                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp, start = 30.dp, end = 30.dp)
                        .height(80.dp)
                        .shadow(elevation = 8.dp, shape = RoundedCornerShape(20.dp))
                        .clickable { navController.navigate(NavScreen.MusicDonut.route) },
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Box(
                            modifier = Modifier
                                .background(
                                    if (sumPriceMusic == 0.0f){
                                        Color.LightGray
                                    } else {
                                        Color(0xFF56bfee)
                                    }, shape = RoundedCornerShape(16.dp) )
                                .size(50.dp),
                        ) {
                            Image(painter = painterResource(id = R.drawable.ic_music),
                                contentDescription = "music streaming",
                                modifier = Modifier
                                    .size(30.dp)
                                    .clip(RoundedCornerShape(20.dp))
                                    .align(Alignment.Center),
                                colorFilter = ColorFilter.tint(color = Color.White)
                            )
                        }
                        Column(
                            modifier = Modifier
                                .width(150.dp)
                                .padding(start = 10.dp),
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .weight(1f),
                                contentAlignment = Alignment.BottomStart,
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    Text(
                                        text = "Music Streaming",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp,
                                        color = colorResource(id = R.color.custom_text),
                                    )
                                    Spacer(modifier = Modifier.width(5.dp))
                                }
                            }
                            Text(
                                text = PriceFormat(price = formattedmusicPrice),
                                modifier = Modifier
                                    .fillMaxSize()
                                    .weight(1f),
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                        }
                    }
                }
            }
        }
    }
}


