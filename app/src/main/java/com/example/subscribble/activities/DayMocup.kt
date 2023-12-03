package com.example.subscribble.activities

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

import androidx.compose.ui.Alignment
import android.content.Context

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import android.graphics.Paint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.subscribble.PriceFormatWeek
import com.example.subscribble.R
import com.example.subscribble.database.module.SubscriptionViewModel
import com.example.subscribble.getApplicationColor
import com.example.subscribble.getDrawableResource
import com.example.subscribble.nameDis
import com.example.subscribble.nameNet
import com.example.subscribble.nameYou
import com.example.subscribble.navbar.NavScreen

@Composable
fun LineChart1(
    xAxisLabels: List<String>,
    yAxisLabels: List<String>
) {
    Canvas(
        modifier = Modifier
            .size(280.dp)
            .background(Color.White)
            .padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 20.dp)
    ) {
        val paint = Paint()
        paint.color = android.graphics.Color.BLACK

        drawLine(
            color = Color.Black,
            start = Offset(0f, size.height+10),
            end = Offset(size.width, size.height +10),
            strokeWidth = 2.dp.toPx()
        )

        drawLine(
            color = Color.Black,
            start = Offset(0f, 0f),
            end = Offset(0f, size.height+10),
            strokeWidth = 2.dp.toPx()
        )


        val xStep = size.width / 6
        val yStep = size.height / 12

        val line1Path = Path()
        val line2Path = Path()
        val line3Path = Path()

        line1Path.moveTo(0 * xStep, size.height - 2 * yStep)
        line1Path.lineTo(1 * xStep, size.height - 9 * yStep)
        line1Path.lineTo(2 * xStep, size.height - 2 * yStep)
        line1Path.lineTo(3 * xStep, size.height - 3 * yStep)
        line1Path.lineTo(4 * xStep, size.height - 1 * yStep)
        line1Path.lineTo(5 * xStep, size.height - 3 * yStep)
        line1Path.lineTo(6 * xStep, size.height - 3 * yStep)

        line2Path.moveTo(0 * xStep, size.height - 6 * yStep)
        line2Path.lineTo(1 * xStep, size.height - 1 * yStep)
        line2Path.lineTo(2 * xStep, size.height - 3 * yStep)
        line2Path.lineTo(3 * xStep, size.height - 5 * yStep)
        line2Path.lineTo(4 * xStep, size.height - 2 * yStep)
        line2Path.lineTo(5 * xStep, size.height - 5 * yStep)
        line2Path.lineTo(6 * xStep, size.height - 2 * yStep)

        line3Path.moveTo(0 * xStep, size.height - 5 * yStep)
        line3Path.lineTo(1 * xStep, size.height - 5 * yStep)
        line3Path.lineTo(2 * xStep, size.height - 7 * yStep)
        line3Path.lineTo(3 * xStep, size.height - 9 * yStep)
        line3Path.lineTo(4 * xStep, size.height - 6 * yStep)
        line3Path.lineTo(5 * xStep, size.height - 1 * yStep)
        line3Path.lineTo(6 * xStep, size.height - 1 * yStep)


        for (i in xAxisLabels.indices) {
            drawIntoCanvas { canvas ->
                val text = xAxisLabels[i]
                val xPos = (i) * size.width / 6
                paint.textSize = 15f
                canvas.nativeCanvas.drawText(
                    text,
                    xPos - (text.length * 5),
                    size.height + 40f,
                    paint
                )
            }
        }
        for (i in yAxisLabels.indices) {
            drawIntoCanvas { canvas ->
                val text = yAxisLabels[i]
                val yPos = size.height - (i + 1) * size.height / yAxisLabels.size
                paint.textSize = 14f
                canvas.nativeCanvas.drawText(
                    text,
                    0f - 25,
                    yPos +15,
                    paint
                )
            }
        }
        drawPath(
            path = line1Path,
            getApplicationColor("Netflix"),
            style = Stroke(width = 1.dp.toPx(), cap = StrokeCap.Round)
        )

        drawPath(
            path = line2Path,
            getApplicationColor("Youtube"),
            style = Stroke(width = 1.dp.toPx(), cap = StrokeCap.Round)
        )

        drawPath(
            path = line3Path,
            getApplicationColor("DisneyPlus"),
            style = Stroke(width = 1.dp.toPx(), cap = StrokeCap.Round)
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DayMocup(context: Context,navController: NavController, subViewmodel: SubscriptionViewModel = hiltViewModel()) {
    val subscription = subViewmodel.subs.collectAsState(initial = emptyList())

    val videoSub = subViewmodel.getSubscriptionByCategory("video")

    Scaffold(
        topBar = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(start = 26.dp, top = 22.dp, bottom = 22.dp)
                    .clickable { navController.navigate(NavScreen.VideoDonut.route) }
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = "Back",
                    modifier = Modifier.size(35.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Video Streaming",
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
                text = "Usage per Week",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
            val xAxisLabels = listOf("Tue", "Wed", "Thu", "Fri", "Sat", "Sun", "Mon")
            val yAxisLabels = listOf(" 2", " 4", " 6", " 8", "10", "12", "14", "16", "18", "20", "22", "24")

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, start = 30.dp, end = 30.dp)
                    .height(300.dp)
                    .shadow(elevation = 8.dp, shape = RoundedCornerShape(20.dp))
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {
                                navController.navigate(NavScreen.DayLine.route)
                            },
                            onDoubleTap = {
                                navController.navigate(NavScreen.TotalMocup.route)
                            }
                        )
                    },
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
                    LineChart1( xAxisLabels, yAxisLabels)
                }
            }
            if (videoSub == null){
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    textAlign = TextAlign.Center,
                    text = "No Video Streaming",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            } else {
                LazyColumn(modifier = Modifier
                    .padding(top = 28.dp, bottom = 40.dp)
                    .fillMaxHeight())
                {
                    items(subscription.value) {subsList ->
                        if (subsList.type == "video"){

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
                                                Box(modifier = Modifier.size(10.dp).background(colorSub, shape = CircleShape).align(Alignment.CenterVertically))
                                            }
                                        }

                                        when (subsList.name) {
                                            nameYou -> {
                                                val hours = (300 / 60).toInt()
                                                val minutes = (599 % 60).toInt()
                                                val totaltime = "%02d.%02d".format(hours, minutes)
                                                Text(
                                                    text = PriceFormatWeek(price ="$totaltime hr"),
                                                    modifier = Modifier.fillMaxSize().weight(1f), fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                            }
                                            nameDis -> {
                                                val hours = (546 / 60).toInt()
                                                val minutes = (333 % 60).toInt()
                                                val totaltime = "%02d.%02d".format(hours, minutes)
                                                Text(text = PriceFormatWeek(price ="$totaltime hr"), modifier = Modifier.fillMaxSize().weight(1f),fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                            }
                                            nameNet -> {
                                                val hours = (343 / 60).toInt()
                                                val minutes = (565 % 60).toInt()
                                                val totaltime = "%02d.%02d".format(hours, minutes)
                                                Text(text = PriceFormatWeek(price ="$totaltime hr"), modifier = Modifier.fillMaxSize().weight(1f), fontWeight = FontWeight.Bold, fontSize = 16.sp)
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
    }
}
