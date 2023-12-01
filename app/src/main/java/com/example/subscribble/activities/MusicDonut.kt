package com.example.subscribble.activities

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.subscribble.PriceFormat
import com.example.subscribble.R
import com.example.subscribble.database.module.SubscriptionViewModel
import com.example.subscribble.getApplicationColor
import com.example.subscribble.getDrawableResource

@Composable
fun doNut2(
    subViewmodel: SubscriptionViewModel = hiltViewModel(),
    size: Dp = 150.dp,
    thickness: Dp = 60.dp
) {

    val priceApple = subViewmodel.getPriceByMusic("AppleMusic")
    val priceSpo = subViewmodel.getPriceByMusic("Spotify")

    val values = listOf(priceApple,priceSpo)

    val sumOfValues = values.sum()
    val colors = listOf(
        getApplicationColor("AppleMusic"),
        getApplicationColor("Spotify"),
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
        val text = "$sumOfValues/month"
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
fun MusicDonut(navController: NavController, subViewmodel: SubscriptionViewModel = hiltViewModel()) {

    val subscription = subViewmodel.subs.collectAsState(initial = emptyList())

    val musicSub = subViewmodel.getSubscriptionByCategory("music")

    Scaffold(
        topBar = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(start = 26.dp, top = 22.dp, bottom = 22.dp)
                    .clickable { navController.popBackStack() }
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = "Back",
                    modifier = Modifier.size(35.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Music Streaming",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = colorResource(id = R.color.custom_text)
                )
            }
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
                    doNut2()
                }
            }

            if (musicSub == null){
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    textAlign = TextAlign.Center,
                    text = "No Music Streaming",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            } else {
                LazyColumn(modifier = Modifier
                    .padding(top = 28.dp, bottom = 40.dp)
                    .fillMaxHeight())
                {
                    items(subscription.value) {subsList ->
                        if (subsList.type == "music"){

                            val colorSub = getApplicationColor(subsList.name)
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp)
                                    .padding(start = 20.dp, end = 20.dp, bottom = 10.dp)
                                    .shadow(
                                        elevation = 8.dp,
                                        shape = RoundedCornerShape(15.dp)
                                    )
                                ,shape = RoundedCornerShape(20.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(start = 10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Image(
                                        painter = painterResource(
                                            id = getDrawableResource(
                                                subsList.name
                                            )
                                        ),
                                        contentDescription = "",
                                        modifier = Modifier
                                            .size(60.dp)
                                            .clip(RoundedCornerShape(20.dp))
                                    )

                                    Column(
                                        modifier = Modifier
                                            .width(150.dp)
                                            .padding(start = 10.dp),
                                    ) {

                                        Box(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .weight(1f),
                                            contentAlignment = Alignment.BottomStart
                                        ) {
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                            ) {
                                                Text(
                                                    text = subsList.name,
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 18.sp,
                                                    color = colorResource(id = R.color.custom_text),
                                                )

                                                Spacer(modifier = Modifier.width(5.dp))

                                                Box(
                                                    modifier = Modifier
                                                        .size(10.dp)
                                                        .background(colorSub, shape = CircleShape)
                                                        .align(Alignment.CenterVertically)
                                                )

                                            }
                                        }
                                        Text(
                                            text = PriceFormat(price = subsList.price.toString()),
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .weight(1f),
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 16.sp

                                        )

                                    }
                                }
                            }
                        }

                    }
                }
            }
        }
    }
}

