package com.example.subscribble.activities


import androidx.compose.foundation.Image
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
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
import com.example.subscribble.navbar.NavScreen
import java.sql.Date
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
                modifier = Modifier
                    .padding(start = 26.dp, top = 22.dp, bottom = 22.dp)
                    //.clickable { navController.popBackStack() }
            ) {
//                Icon(
//                    imageVector = Icons.Default.KeyboardArrowLeft,
//                    contentDescription = "Back",
//                    modifier = Modifier.size(35.dp)
//                )
//                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Upcoming Bills",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )

                Spacer(modifier = Modifier.width(200.dp))

                Text(
                    text = "Days left",
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = Color.Gray
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
//                    val dateFormat = SimpleDateFormat("dd/MM/yyyy")
//                    val date = dateFormat.parse(dateSubs)
//                    val formattedDate = SimpleDateFormat("d MMM").format(date)
//                    val dueDate = getDueDateCountDown(dateSubs)

                    val daysLeft = getDueDateCountDown(dateSubs)

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .padding(start = 20.dp, end = 20.dp, bottom = 10.dp)
                            .shadow(elevation = 8.dp, shape = RoundedCornerShape(15.dp))
                            .clickable { navController.navigate(NavScreen.ShowDetailScreen.route + "/${subsList.id}") },

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
                                    text = PriceFormat(price = subsList.price.toString()),
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
                                //Text(text = dueDate, fontSize = 16.sp, fontWeight = FontWeight.Medium, color = colorResource(id = R.color.custom_text))

                                if (daysLeft == "payment due date" || daysLeft.toInt() <= 7){
                                    Text(text = daysLeft, fontSize = 16.sp, fontWeight = FontWeight.Medium,
                                        color = colorResource(id = R.color.custom_alert)
                                    )
                                } else {
                                    Text(text = daysLeft, fontSize = 16.sp, fontWeight = FontWeight.Medium,
                                        color = colorResource(id = R.color.custom_text)
                                    )
                                }

//                                Text(text = daysLeft, fontSize = 16.sp, fontWeight = FontWeight.Medium,
//                                    color = colorResource(id = R.color.custom_text)
//                                )

                            }
                        }
                    }

                }

//                items(subscription.value) { subsList ->
//                    //SubscriptionList(subsList.id, subsList.name, subsList.price,)
//
//                    val dateSubs = subsList.date
////                    val dateFormat = SimpleDateFormat("dd/MM/yyyy")
////                    val date = dateFormat.parse(dateSubs)
////                    val formattedDate = SimpleDateFormat("d MMM").format(date)
////                    val dueDate = getDueDateCountDown(dateSubs)
//
//                    val daysLeft = getDueDateCountDown(dateSubs)
//
//                    Card(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(100.dp)
//                            .padding(start = 20.dp, end = 20.dp, bottom = 10.dp)
//                            .shadow(elevation = 8.dp, shape = RoundedCornerShape(15.dp))
//                            .clickable { navController.navigate(NavScreen.ShowDetailScreen.route + "/${subsList.id}") },
//
//                        shape = RoundedCornerShape(20.dp),
//                        colors = CardDefaults.cardColors(containerColor = Color.White)
//                    ) {
//                        Row(
//                            modifier = Modifier
//                                .fillMaxSize()
//                                .padding(start = 10.dp),
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//
//                            Image(
//                                painter = painterResource(id = getDrawableResource(subsList.name)),
//                                contentDescription = "",
//                                modifier = Modifier
//                                    .size(60.dp)
//                                    .clip(RoundedCornerShape(20.dp))
//                            )
//
//                            Column(
//                                modifier = Modifier
//                                    .width(150.dp)
//                                    .padding(start = 10.dp),
//                            ) {
//
//                                Box(
//                                    modifier = Modifier
//                                        .fillMaxSize()
//                                        .weight(1f),
//                                    contentAlignment = Alignment.BottomStart
//                                ) {
//                                    Row(
//                                        modifier = Modifier
//                                            .fillMaxWidth()
//                                    ) {
//                                        Text(
//                                            text = subsList.name,
//                                            fontWeight = FontWeight.Bold,
//                                            fontSize = 18.sp,
//                                            color = colorResource(id = R.color.custom_text),
//                                        )
//
//                                        Spacer(modifier = Modifier.width(5.dp))
//
//                                    }
//                                }
//                                Text(
//                                    text = PriceFormat(price = subsList.price.toString()),
//                                    modifier = Modifier
//                                        .fillMaxSize()
//                                        .weight(1f),
//                                    fontWeight = FontWeight.Bold,
//                                    fontSize = 16.sp
//
//                                )
//
//                            }
//
//                            Box(
//                                modifier = Modifier
//                                    .fillMaxSize()
//                                    .padding(end = 20.dp)
//                                ,contentAlignment = Alignment.CenterEnd
//                            ){
//                                //Text(text = dueDate, fontSize = 16.sp, fontWeight = FontWeight.Medium, color = colorResource(id = R.color.custom_text))
//
//                                if (daysLeft == "payment due date" || daysLeft.toInt() <= 7){
//                                    Text(text = daysLeft, fontSize = 16.sp, fontWeight = FontWeight.Medium,
//                                        color = colorResource(id = R.color.custom_alert)
//                                    )
//                                } else {
//                                    Text(text = daysLeft, fontSize = 16.sp, fontWeight = FontWeight.Medium,
//                                        color = colorResource(id = R.color.custom_text)
//                                    )
//                                }
//
////                                Text(text = daysLeft, fontSize = 16.sp, fontWeight = FontWeight.Medium,
////                                    color = colorResource(id = R.color.custom_text)
////                                )
//
//                            }
//                        }
//                    }
//
//                }

            }
        }
    }
}



fun getDueDateCountDown(dueDate: String): String {

    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    try {
        val dueDateParsed = formatter.parse(dueDate)
        val currentDate = Date(System.currentTimeMillis())
        var daysUntilDueDate = TimeUnit.MILLISECONDS.toDays(dueDateParsed.time - currentDate.time)

        if (daysUntilDueDate < 0) {

            val nextDueDate = Calendar.getInstance()
            nextDueDate.time = dueDateParsed
            while (nextDueDate.time.before(currentDate)) {
                nextDueDate.add(Calendar.MONTH, 1)
            }
            daysUntilDueDate = TimeUnit.MILLISECONDS.toDays(nextDueDate.time.time - currentDate.time)
        }

        return if (daysUntilDueDate.toInt() == 0) {
            "0"
        } else {
            daysUntilDueDate.toString()
        }
    } catch (e: ParseException){
        return "Invalid due date format: ${e.message}"
    }
//    val dueDateParsed = formatter.parse(dueDate)
//    val currentDate = Date(System.currentTimeMillis())
//    var daysUntilDueDate = TimeUnit.MILLISECONDS.toDays(dueDateParsed.time - currentDate.time)
//
//    if (daysUntilDueDate < 0) {
//
//        val nextDueDate = Calendar.getInstance()
//        nextDueDate.time = dueDateParsed
//        while (nextDueDate.time.before(currentDate)) {
//            nextDueDate.add(Calendar.MONTH, 1)
//        }
//        daysUntilDueDate = TimeUnit.MILLISECONDS.toDays(nextDueDate.time.time - currentDate.time)
//    }
//
//    return if (daysUntilDueDate.toInt() == 0) {
//        "ถึงวันครบกำหนดชำระ"
//    } else {
//        daysUntilDueDate.toString()
//    }
}

