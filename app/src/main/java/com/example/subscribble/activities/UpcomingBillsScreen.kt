package com.example.subscribble.activities


import androidx.compose.foundation.Image
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.subscribble.PriceFormat
import com.example.subscribble.R
import com.example.subscribble.database.module.SubscriptionViewModel
import com.example.subscribble.getDrawableResource
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpcomingBillsScreen(navController: NavController, subViewmodel: SubscriptionViewModel = hiltViewModel()) {

    val subscription = subViewmodel.subs.collectAsState(initial = emptyList())

    LaunchedEffect(key1 = subViewmodel){
        subViewmodel.loadSubs()
    }

    Scaffold(
        topBar = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(start = 26.dp, top = 22.dp, bottom = 22.dp)
            ) {
                Text(
                    text = "Upcoming Bills",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = colorResource(id = R.color.custom_text)
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "Days left",
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(end = 26.dp)
                )
            }
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            LazyColumn(modifier = Modifier
                .padding(top = 28.dp, bottom = 40.dp)
                .fillMaxHeight()
            )
            {
                val sortedSubscriptionList = subscription.value.sortedBy { subList ->
                    getDueDateCountDown(subList.date).toInt()
                }

                items(sortedSubscriptionList) {subsList ->

                    val dateSubs = subsList.date

                    val daysLeft = getDueDateCountDown(dateSubs)

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .padding(start = 20.dp, end = 20.dp, bottom = 10.dp)
                            .shadow(elevation = 8.dp, shape = RoundedCornerShape(15.dp))
                            //.clickable { navController.navigate(NavScreen.ShowDetailScreen.route + "/${subsList.id}") }
                        ,

                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(start = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Image(
                                painter = painterResource(id = getDrawableResource(subsList.name)),
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

                                    }
                                }
                                Text(
                                    text = PriceFormat(price = String.format("%.2f", subsList.price)),
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .weight(1f),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp

                                )

                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(end = 20.dp)
                                ,contentAlignment = Alignment.CenterEnd
                            ){
                                
                                if (daysLeft == "payment due date" || daysLeft.toInt() <= 7){
                                    Text(text = daysLeft, fontSize = 16.sp, fontWeight = FontWeight.Medium,
                                        color = colorResource(id = R.color.custom_alert)
                                    )
                                } else {
                                    Text(text = daysLeft, fontSize = 16.sp, fontWeight = FontWeight.Medium,
                                        color = colorResource(id = R.color.custom_text)
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



fun getDueDateCountDown(dueDate: String): String {

    val formatter = SimpleDateFormat("dd", Locale.getDefault())

    try {
        val dueDateParsed = formatter.parse(dueDate)
        val calendar = Calendar.getInstance()
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
        val dueDateTest = formatter.parse(currentDay.toString())
        //val currentDate = Date(System.currentTimeMillis())
        var daysUntilDueDate = TimeUnit.MILLISECONDS.toDays(dueDateParsed.time - dueDateTest.time)

        if (daysUntilDueDate < 0) {

            val nextDueDate = Calendar.getInstance()
            nextDueDate.time = dueDateParsed
            while (nextDueDate.time.before(dueDateTest)) {
                nextDueDate.add(Calendar.MONTH, 1)
            }
            daysUntilDueDate = TimeUnit.MILLISECONDS.toDays(nextDueDate.time.time - dueDateTest.time)
        }

        return if (daysUntilDueDate.toInt() == 0) {
            "0"
        } else {
            daysUntilDueDate.toString()
        }
    } catch (e: ParseException){
        return "Invalid due date format: ${e.message}"
    }
}
