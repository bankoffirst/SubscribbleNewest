package com.example.subscribble.activities

import android.app.AlertDialog
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
import android.app.AppOpsManager
import android.provider.Settings
import android.content.Intent

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
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
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.subscribble.navbar.NavScreen
import androidx.navigation.NavController
import com.example.subscribble.PriceFormat
import com.example.subscribble.R
import com.example.subscribble.database.module.SubscriptionViewModel
import com.example.subscribble.getApplicationColor
import com.example.subscribble.getDrawableResource
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.foundation.gestures.detectTapGestures
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TotalLine(context: Context,navController: NavController, subViewmodel: SubscriptionViewModel = hiltViewModel()) {

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
                text = "Usage per Month",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )

            val nameYou = subViewmodel.getNameByName("Youtube")
            val nameDis = subViewmodel.getNameByName("DisneyPlus")
            val nameNet = subViewmodel.getNameByName("Netflix")

            val youtubeDataPoints = getUsageStatsForWeeks(context, "com.google.android.youtube")
            val disneyplusDataPoints = getUsageStatsForWeeks(context, "com.disney.disneyplus")
            val netflixDataPoints = getUsageStatsForWeeks(context, "com.netflix.mediaclient")

            val totalyou =  String.format("%.2f", youtubeDataPoints.sum()).toFloat()
            val totaldis =  String.format("%.2f", disneyplusDataPoints.sum()).toFloat()
            val totalnet =  String.format("%.2f", netflixDataPoints.sum()).toFloat()

            val xAxisLabels = listOf("Week1", "Week2", "Week3", "Week4")
            val yAxisLabels = listOf(" 10", " 20", " 30", " 40", " 50", " 60", " 70", " 80", " 90", "100", "110", "120", "130", "140", "150")

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, start = 30.dp, end = 30.dp)
                    .height(300.dp)
                    .shadow(elevation = 8.dp, shape = RoundedCornerShape(20.dp))
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {
                                navController.navigate(NavScreen.VideoDonut.route)
                            }, onDoubleTap = {
                                navController.navigate(NavScreen.DayLine.route)
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
                    if (checkForUsagePermission(context) && videoSub == null) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 20.dp),
                            textAlign = TextAlign.Center,
                            text = "No Video Streaming",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    } else if(!checkForUsagePermission(context)) {
                        Button(onClick = {
                            navController.navigate(NavScreen.TotalLine.route)
                            showPermissionDialog(context)
                        }) {
                            Text("Check access")
                        }
                    }else{
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
                            val xStep = size.width / (xAxisLabels.size - 1)
                            val yStep = size.height / 15 / 720

                            val youtubePath = Path()
                            val disneyplusPath = Path()
                            val netflixDataPath = Path()

                            drawLine(
                                color = Color.Black,
                                start = Offset(0f, size.height + 10),
                                end = Offset(size.width, size.height + 10),
                                strokeWidth = 2.dp.toPx()
                            )

                            drawLine(
                                color = Color.Black,
                                start = Offset(0f, 0f),
                                end = Offset(0f, size.height + 10),
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
                                        0f - 30,
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
                                                youtubePath.lineTo(x, y)
                                                drawContext.canvas.nativeCanvas.drawText(
                                                    xAxisLabels.getOrNull(index) ?: "",
                                                    x,
                                                    size.height + 40,
                                                    paint
                                                )
                                                val hours = (value / 60).toInt()
                                                val minutes = (value % 60).toInt()
                                                val timeLabel = "%02d:%02d".format(hours, minutes)
                                                drawContext.canvas.nativeCanvas.drawText(
                                                    timeLabel,
                                                    x,
                                                    y - 5.dp.toPx(),
                                                    paint
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
                                                disneyplusPath.lineTo(x, y)
                                                drawContext.canvas.nativeCanvas.drawText(
                                                    xAxisLabels.getOrNull(index) ?: "",
                                                    x,
                                                    size.height + 40,
                                                    paint
                                                )
                                                val hours = (value / 60).toInt()
                                                val minutes = (value % 60).toInt()
                                                val timeLabel = "%02d:%02d".format(hours, minutes)
                                                drawContext.canvas.nativeCanvas.drawText(
                                                    timeLabel,
                                                    x,
                                                    y - 5.dp.toPx(),
                                                    paint
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
                                                netflixDataPath.lineTo(x, y)
                                                drawContext.canvas.nativeCanvas.drawText(
                                                    xAxisLabels.getOrNull(index) ?: "",
                                                    x,
                                                    size.height + 40,
                                                    paint
                                                )
                                                val hours = (value / 60).toInt()
                                                val minutes = (value % 60).toInt()
                                                val timeLabel = "%02d.%02d".format(hours, minutes)
                                                drawContext.canvas.nativeCanvas.drawText(
                                                    timeLabel,
                                                    x,
                                                    y - 5.dp.toPx(),
                                                    paint
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
                                                Box(modifier = Modifier.size(10.dp).background(colorSub, shape = CircleShape).align(Alignment.CenterVertically))
                                            }
                                        }
                                        when (subsList.name) {
                                            nameYou -> {
                                                val hours = (totalyou / 60).toInt()
                                                val minutes = (totalyou % 60).toInt()
                                                val totaltime = "%02d.%02d".format(hours, minutes)
                                                Text(
                                                    text = PriceFormat(price ="$totaltime hr"),
                                                    modifier = Modifier.fillMaxSize().weight(1f), fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                            }
                                            nameDis -> {
                                                val hours = (totaldis / 60).toInt()
                                                val minutes = (totaldis % 60).toInt()
                                                val totaltime = "%02d.%02d".format(hours, minutes)
                                                Text(text = PriceFormat(price ="$totaltime hr"), modifier = Modifier.fillMaxSize().weight(1f),fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                            }
                                            nameNet -> {
                                                val hours = (totalnet / 60).toInt()
                                                val minutes = (totalnet % 60).toInt()
                                                val totaltime = "%02d.%02d".format(hours, minutes)
                                                Text(text = PriceFormat(price ="$totaltime hr"), modifier = Modifier.fillMaxSize().weight(1f), fontWeight = FontWeight.Bold, fontSize = 16.sp)
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


fun checkForUsagePermission(context: Context): Boolean {
    val appOps = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
    val mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, android.os.Process.myUid(), context.packageName)
    return mode == AppOpsManager.MODE_ALLOWED
}


private fun showPermissionDialog(context: Context) {
    val message = if (checkForUsagePermission(context)) {
        "Access complete"
    } else {
        "This app requires access to your usage data in order to provide statistics and personal insights about your app usage"
    }
    AlertDialog.Builder(context)
        .setTitle("Request Permission")
        .setMessage(message)
        .setPositiveButton("OK") { dialog, which ->
            if(!checkForUsagePermission(context)){
                val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
                context.startActivity(intent)
            }
        }
        .setNegativeButton("Cancel") { dialog, which ->
        }
        .show()
}
