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

import android.app.usage.UsageStatsManager
import android.content.Context

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import java.util.Calendar
import android.graphics.Paint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.subscribble.PriceFormat
import com.example.subscribble.R
import com.example.subscribble.database.module.SubscriptionViewModel
import com.example.subscribble.getApplicationColor
import com.example.subscribble.getDrawableResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DayLine(context: Context,navController: NavController, subViewmodel: SubscriptionViewModel = hiltViewModel()) {

    val subscription = subViewmodel.subs.collectAsState(initial = emptyList())

    val videoSub = subViewmodel.getSubscriptionByCategory("video")

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
                    text = "Video Streaming",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
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
                text = "Usage per Days",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
            val nameYou = subViewmodel.getNameByName("Youtube")
            val nameDis = subViewmodel.getNameByName("DisneyPlus")
            val nameNet = subViewmodel.getNameByName("Netflix")


            val youtubeDataPoints = getUsageStatsForDays(context, "com.google.android.youtube")
            val disneyplusDataPoints = getUsageStatsForDays(context, "com.disney.disneyplus")
            val netflixDataPoints = getUsageStatsForDays(context, "com.netflix.mediaclient")

            val xAxisLabels = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
            val yAxisLabels = listOf(" 2", " 4", " 6", " 8", " 10", "12", "14", "16", "18", "20", "22", "24")
            val reorderedXAxisLabels = xAxisLabels.drop(getCurrentDayIndex()).plus(xAxisLabels.take(getCurrentDayIndex()))
            val totalyou =  String.format("%.2f", youtubeDataPoints.sum()).toFloat()
            val totaldis =  String.format("%.2f", disneyplusDataPoints.sum()).toFloat()
            val totalnet =  String.format("%.2f", netflixDataPoints.sum()).toFloat()

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, start = 30.dp, end = 30.dp)
                    .height(300.dp)
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
                    if (videoSub == null) {
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
                        Canvas(
                            modifier = Modifier
                                .size(280.dp)
                                .background(Color.White)
                                .padding(
                                    start = 20.dp,
                                    end = 20.dp,
                                    top = 20.dp,
                                    bottom = 20.dp
                                )
                        ) {
                            val paint = Paint()
                            paint.color = android.graphics.Color.BLACK
                            val minValue = minOf(
                                youtubeDataPoints.minOrNull() ?: 0.0f,
                                disneyplusDataPoints.minOrNull() ?: 0.0f,
                                netflixDataPoints.minOrNull() ?: 1.0f
                            )
                            val xStep = size.width / (reorderedXAxisLabels.size - 1)
                            val yStep = size.height / 12 / 140

                            val youtubePath = Path()
                            val disneyplusPath = Path()
                            val netflixDataPath = Path()

                            drawLine(
                                color = Color.Black,
                                start = Offset(0f, size.height+10),
                                end = Offset(size.width, size.height+10),
                                strokeWidth = 2.dp.toPx()
                            )

                            drawLine(
                                color = Color.Black,
                                start = Offset(0f, 0f),
                                end = Offset(0f, size.height+10),
                                strokeWidth = 2.dp.toPx()
                            )
                            youtubePath.moveTo(0f, size.height - (youtubeDataPoints[0] - minValue) * yStep)
                            disneyplusPath.moveTo(0f, size.height - (disneyplusDataPoints[0] - minValue) * yStep)
                            netflixDataPath.moveTo(0f, size.height - (netflixDataPoints[0] - minValue) * yStep)

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
                            val subscriptions = subscription.value
                            for(subsList in subscriptions){
                                if (subsList.type == "video") {
                                    when (subsList.name) {
                                        nameYou -> {
                                            youtubeDataPoints.forEachIndexed { index, value ->
                                                val x = index * xStep
                                                val y = size.height - (value - minValue) * yStep
                                                val timeSize = 15f
                                                youtubePath.lineTo(x, y)
                                                drawContext.canvas.nativeCanvas.drawText(
                                                    reorderedXAxisLabels.getOrNull(index) ?: "",
                                                    x,
                                                    size.height + 40,
                                                    paint.apply {
                                                        textSize = timeSize
                                                    }
                                                )
                                                val hours = (value / 60).toInt()
                                                val minutes = (value % 60).toInt()
                                                val timeLabel = "%02d:%02d".format(hours, minutes)

                                                drawContext.canvas.nativeCanvas.drawText(
                                                    timeLabel,
                                                    x,
                                                    y - 10.dp.toPx(),
                                                    paint.apply {
                                                        textSize = timeSize
                                                    }
                                                )
                                            }
                                            drawPath(
                                                path = youtubePath,
                                                getApplicationColor("Youtube"),
                                                style = Stroke(
                                                    width = 1.dp.toPx(),
                                                    cap = StrokeCap.Round
                                                )
                                            )
                                        }
                                        nameDis -> {
                                            disneyplusDataPoints.forEachIndexed { index, value ->
                                                val x = index * xStep
                                                val y = size.height - (value - minValue) * yStep
                                                val timeSize = 15f
                                                disneyplusPath.lineTo(x, y)
                                                drawContext.canvas.nativeCanvas.drawText(
                                                    reorderedXAxisLabels.getOrNull(index) ?: "",
                                                    x,
                                                    size.height + 40,
                                                    paint.apply {
                                                        textSize = timeSize
                                                    }
                                                )
                                                val hours = (value / 60).toInt()
                                                val minutes = (value % 60).toInt()
                                                val timeLabel = "%02d:%02d".format(hours, minutes)
                                                drawContext.canvas.nativeCanvas.drawText(
                                                    timeLabel,
                                                    x,
                                                    y - 10.dp.toPx(),
                                                    paint.apply {
                                                        textSize = timeSize
                                                    }
                                                )
                                            }
                                            drawPath(
                                                path = disneyplusPath,
                                                getApplicationColor("DisneyPlus"),
                                                style = Stroke(
                                                    width = 1.dp.toPx(),
                                                    cap = StrokeCap.Round
                                                )
                                            )
                                        }
                                        nameNet -> {
                                            netflixDataPoints.forEachIndexed { index, value ->
                                                val x = index * xStep
                                                val y = size.height - (value - minValue) * yStep
                                                val timeSize = 15f
                                                netflixDataPath.lineTo(x, y)

                                                drawContext.canvas.nativeCanvas.drawText(
                                                    reorderedXAxisLabels.getOrNull(index) ?: "",
                                                    x,
                                                    size.height + 40,

                                                    paint.apply {
                                                        textSize = timeSize
                                                    }
                                                )

                                                val hours = (value / 60).toInt()
                                                val minutes = (value / 60).toInt()
                                                drawContext.canvas.nativeCanvas.drawText(
                                                    "%02d:%02d".format(hours, minutes),
                                                    x,
                                                    y - 10.dp.toPx(),
                                                    paint.apply {
                                                        textSize = timeSize
                                                    }
                                                )
                                            }
                                            drawPath(
                                                path = netflixDataPath,
                                                getApplicationColor("Netflix"),
                                                style = Stroke(
                                                    width = 1.dp.toPx(),
                                                    cap = StrokeCap.Round
                                                )
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
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

                                                Box(
                                                    modifier = Modifier
                                                        .size(10.dp)
                                                        .background(colorSub, shape = CircleShape)
                                                        .align(Alignment.CenterVertically)
                                                )

                                            }
                                        }
                                        when (subsList.name) {
                                            nameYou -> {
                                                val hours = (totalyou / 60).toInt()
                                                val minutes = (totalyou % 60).toInt()
                                                val totaltime = "%02d.%02d".format(hours, minutes)
                                                Text(
                                                    text = PriceFormat(price ="$totaltime hr"),
                                                    modifier = Modifier
                                                        .fillMaxSize()
                                                        .weight(1f),
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 16.sp
                                                )
                                            }
                                            nameDis -> {
                                                val hours = (totaldis / 60).toInt()
                                                val minutes = (totaldis % 60).toInt()
                                                val totaltime = "%02d.%02d".format(hours, minutes)
                                                Text(
                                                    text = PriceFormat(price ="$totaltime hr"),
                                                    modifier = Modifier
                                                        .fillMaxSize()
                                                        .weight(1f),
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 16.sp
                                                )

                                            }
                                            nameNet -> {
                                                val hours = (totalnet / 60).toInt()
                                                val minutes = (totalnet % 60).toInt()
                                                val totaltime = "%02d.%02d".format(hours, minutes)
                                                Text(
                                                    text = PriceFormat(price ="$totaltime hr"),
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
    }
}


fun getCurrentDayIndex(): Int {
    val currentDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
    return (currentDay - 1) % 7
}

@Composable
fun getUsageStatsForDays(context: Context, packageName: String): List<Float> {
    val usageStatsManager = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
    val calendar = Calendar.getInstance()

    val dataPoints = mutableListOf<Float>()

    for (dayIndex in 0 until 7) {
        calendar.timeInMillis = System.currentTimeMillis()

        calendar.add(Calendar.DAY_OF_WEEK, -dayIndex)

        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val startTime = calendar.timeInMillis

        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        val endTime = calendar.timeInMillis

        val appUsageData = usageStatsManager.queryUsageStats(
            UsageStatsManager.INTERVAL_DAILY,
            startTime,
            endTime
        )

        var dailyUsage = 0f

        for (usageStats in appUsageData) {
            if (usageStats.packageName == packageName) {
                dailyUsage = usageStats.totalTimeInForeground / (1000 * 60).toFloat()
            }
        }
        dataPoints.add(dailyUsage)
    }

    return dataPoints.reversed()
}
