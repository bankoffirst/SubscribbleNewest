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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import com.example.subscribble.navbar.NavScreen
import androidx.navigation.NavController

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


        val xStep = size.width / 7
        val yStep = size.height / 12

        val line1Path = Path()
        val line2Path = Path()
        val line3Path = Path()
        val line4Path = Path()

        line1Path.moveTo(0f, size.height - 0 * yStep)
        line1Path.lineTo(1 * xStep, size.height - 9 * yStep)
        line1Path.lineTo(2 * xStep, size.height - 2 * yStep)
        line1Path.lineTo(3 * xStep, size.height - 3 * yStep)
        line1Path.lineTo(4 * xStep, size.height - 1 * yStep)
        line1Path.lineTo(5 * xStep, size.height - 3 * yStep)
        line1Path.lineTo(6 * xStep, size.height - 3 * yStep)
        line1Path.lineTo(7 * xStep, size.height - 1 * yStep)

        line2Path.moveTo(0f, size.height - 0 * yStep)
        line2Path.lineTo(1 * xStep, size.height - 1 * yStep)
        line2Path.lineTo(2 * xStep, size.height - 3 * yStep)
        line2Path.lineTo(3 * xStep, size.height - 5 * yStep)
        line2Path.lineTo(4 * xStep, size.height - 2 * yStep)
        line2Path.lineTo(5 * xStep, size.height - 5 * yStep)
        line2Path.lineTo(6 * xStep, size.height - 2 * yStep)
        line2Path.lineTo(7 * xStep, size.height - 0 * yStep)

        line3Path.moveTo(0f, size.height - 0 * yStep)
        line3Path.lineTo(1 * xStep, size.height - 5 * yStep)
        line3Path.lineTo(2 * xStep, size.height - 7 * yStep)
        line3Path.lineTo(3 * xStep, size.height - 9 * yStep)
        line3Path.lineTo(4 * xStep, size.height - 6 * yStep)
        line3Path.lineTo(5 * xStep, size.height - 1 * yStep)
        line3Path.lineTo(6 * xStep, size.height - 1 * yStep)
        line3Path.lineTo(7 * xStep, size.height - 3 * yStep)

        line4Path.moveTo(0f, size.height - 0 * yStep)
        line4Path.lineTo(1 * xStep, size.height - 2 * yStep)
        line4Path.lineTo(2 * xStep, size.height - 6 * yStep)
        line4Path.lineTo(3 * xStep, size.height - 1 * yStep)
        line4Path.lineTo(4 * xStep, size.height - 4 * yStep)
        line4Path.lineTo(5 * xStep, size.height - 9 * yStep)
        line4Path.lineTo(6 * xStep, size.height - 0 * yStep)
        line4Path.lineTo(7 * xStep, size.height - 0 * yStep)

        for (i in xAxisLabels.indices) {
            drawIntoCanvas { canvas ->
                val text = xAxisLabels[i]
                val xPos = (i + 1) * size.width / xAxisLabels.size

                canvas.nativeCanvas.drawText(
                    text,
                    xPos - (text.length * 8),
                    size.height + 30f,
                    paint
                )
            }
        }
        for (i in yAxisLabels.indices) {
            drawIntoCanvas { canvas ->
                val text = yAxisLabels[i]
                val yPos = size.height - (i + 1) * size.height / yAxisLabels.size

                canvas.nativeCanvas.drawText(
                    text,
                    0f -20,
                    yPos + 10,
                    paint
                )
            }
        }
        drawPath(
            path = line1Path,
            color = Color(android.graphics.Color.parseColor("#fc6c29")),
            style = Stroke(width = 1.dp.toPx(), cap = StrokeCap.Round)
        )

        drawPath(
            path = line2Path,
            color = Color(android.graphics.Color.parseColor("#2937fc")),
            style = Stroke(width = 1.dp.toPx(), cap = StrokeCap.Round)
        )

        drawPath(
            path = line3Path,
            color = Color(android.graphics.Color.parseColor("#00c0ad")),
            style = Stroke(width = 1.dp.toPx(), cap = StrokeCap.Round)
        )
        drawPath(
            path = line4Path,
            color = Color(android.graphics.Color.parseColor("#c00000")),
            style = Stroke(width = 1.dp.toPx(), cap = StrokeCap.Round)
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TotalMocup(context: Context,navController: NavController) {

    val hasUsagePermission = checkForUsagePermission(context)

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
            val xAxisLabels = listOf("1", "2", "3", "4", "5", "6", "7")
            val yAxisLabels = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12")

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, start = 30.dp, end = 30.dp)
                    .height(300.dp)
                    .shadow(elevation = 8.dp, shape = RoundedCornerShape(20.dp)),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                LineChart1( xAxisLabels, yAxisLabels)
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
