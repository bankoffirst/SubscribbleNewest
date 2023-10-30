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
import android.app.AppOpsManager
import android.provider.Settings
import android.content.Intent

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import java.util.Calendar
import android.graphics.Paint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.nativeCanvas
import com.example.subscribble.navbar.NavScreen
import androidx.navigation.NavController
@Composable
fun LineChart(
    youtubeDataPoints: List<Float>,
    messengerDataPoints: List<Float>,
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
        val maxValue = maxOf(
            youtubeDataPoints.maxOrNull() ?: 1.0f,
            messengerDataPoints.maxOrNull() ?: 1.0f
        )
        val minValue = minOf(
            youtubeDataPoints.minOrNull() ?: 0.0f,
            messengerDataPoints.minOrNull() ?: 0.0f
        )
        val xStep = size.width / (maxOf(youtubeDataPoints.size, messengerDataPoints.size) - 1)
        val yStep = size.height / (maxValue - minValue)

        val youtubePath = Path()
        val messengerPath = Path()

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
        messengerPath.moveTo(0f, size.height - (messengerDataPoints[0] - minValue) * yStep)

        youtubeDataPoints.forEachIndexed { index, value ->
            val x = index * xStep
            val y = size.height - (value - minValue) * yStep
            youtubePath.lineTo(x, y)

            drawContext.canvas.nativeCanvas.drawText(
                xAxisLabels.getOrNull(index) ?: "",
                x,
                size.height + 30,
                paint
            )
        }

        messengerDataPoints.forEachIndexed { index, value ->
            val x = index * xStep
            val y = size.height - (value - minValue) * yStep
            messengerPath.lineTo(x, y)
            drawContext.canvas.nativeCanvas.drawText(
                yAxisLabels.getOrNull(index) ?: "",
                -10f,
                y,
                paint
            )
        }

        drawPath(
            path = youtubePath,
            color = Color.Red,
            style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round)
        )

        drawPath(
            path = messengerPath,
            color = Color.Blue,
            style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TotalLine(context: Context,navController: NavController) {

    val hasUsagePermission = checkForUsagePermission(context)

    Scaffold(
        topBar = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(start = 26.dp, top = 22.dp, bottom = 22.dp)
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
                text = "Usage per month",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
            if (hasUsagePermission) {

            }else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Text(
                        text = "No usage permission granted.",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        modifier = Modifier.padding(start = 26.dp, top = 10.dp)
                    )
                }

                requestUsagePermission(context)
            }

            val numberOfWeeks = 4
            val youtubeDataPoints = getUsageStatsForWeeks(context, "com.google.android.youtube", numberOfWeeks)
            val messengerDataPoints = getUsageStatsForWeeks(context, "com.facebook.orca", numberOfWeeks)

            val xAxisLabels = (1..numberOfWeeks).map { "Week $it" }

            val maxValueY = (maxOf(
                youtubeDataPoints.maxOrNull() ?: 0.0f,
                messengerDataPoints.maxOrNull() ?: 0.0f
            ) / 60).toInt()


            val yAxisLabels = (0..maxValueY).map { it.toString() }


            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, start = 30.dp, end = 30.dp)
                    .height(300.dp)
                    .shadow(elevation = 8.dp, shape = RoundedCornerShape(20.dp)),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                LineChart(youtubeDataPoints, messengerDataPoints, xAxisLabels, yAxisLabels)
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding( top = 20.dp,start = 30.dp, end = 30.dp)
                    .height(80.dp)
                    .shadow(elevation = 8.dp, shape = RoundedCornerShape(20.dp)),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Text(
                    text = "App1",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(start = 26.dp, top = 10.dp)
                )
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, start = 30.dp, end = 30.dp)
                    .height(80.dp)
                    .shadow(elevation = 8.dp, shape = RoundedCornerShape(20.dp)),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Text(
                    text = "App2",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(start = 26.dp, top = 10.dp)
                )
            }
        }
    }
}

private fun checkForUsagePermission(context: Context): Boolean {
    val appOps = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
    val mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, android.os.Process.myUid(), context.packageName)
    return mode == AppOpsManager.MODE_ALLOWED
}

private fun requestUsagePermission(context: Context) {
    val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
    context.startActivity(intent)
}

private fun getUsageStatsForWeeks(context: Context, packageName: String, numberOfWeeks: Int): List<Float> {
    val usageStatsManager = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
    val calendar = Calendar.getInstance()

    val dataPoints = mutableListOf<Float>()

    for (i in 0 until numberOfWeeks) {
        calendar.set(Calendar.WEEK_OF_MONTH, i + 1)
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
        val startTime = calendar.timeInMillis

        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY)
        val endTime = calendar.timeInMillis

        val appUsageData = usageStatsManager.queryUsageStats(
            UsageStatsManager.INTERVAL_DAILY,
            startTime,
            endTime
        )

        var weeklyUsage = 0f

        for (usageStats in appUsageData) {
            if (usageStats.packageName == packageName) {
                weeklyUsage += usageStats.totalTimeInForeground / (1000 * 60).toFloat()
            }
        }

        dataPoints.add(weeklyUsage)
    }

    return dataPoints
}
