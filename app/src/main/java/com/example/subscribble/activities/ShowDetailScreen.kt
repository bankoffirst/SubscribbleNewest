package com.example.subscribble.activities

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.subscribble.PriceFormat
import com.example.subscribble.R
import com.example.subscribble.database.module.SubscriptionViewModel
import com.example.subscribble.getDrawableResource
import com.example.subscribble.navbar.NavScreen
import androidx.compose.material.AlertDialog
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowDetailScreen(context: Context, navController: NavController, subsId: Int, subViewmodel: SubscriptionViewModel = hiltViewModel()) {

    val subscription = subViewmodel.getSubscriptionById(subsId)

    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = subViewmodel) {
        subViewmodel.loadSubs()
        subViewmodel.loadCards()
    }

    subscription?.let { subs ->
        val app = subs.name
        val price = PriceFormat(subs.price.toString())
        val plan = subs.planName
        val date = subs.date
        val note = subs.note
        var card = subs.cardName

        val getUsage = subViewmodel.getUsageByName(app)

        card = if (card == "") {
            "-"
        } else {
            subs.cardName
        }

        Scaffold(
            topBar = {
                Row(
                    Modifier.padding(start = 26.dp, top = 22.dp, bottom = 22.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_back_ios),
                            contentDescription = "arrow back",
                            tint = colorResource(id = R.color.custom_text)
                        )
                    }
                    Text(
                        text = app,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = colorResource(id = R.color.custom_text),
                        modifier = Modifier.padding(start = 2.dp)
                    )
                }
            }
        ) { contentPadding ->

            LazyColumn{
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(contentPadding)
                    ) {

                        Card(
                            modifier = Modifier
                                .height(850.dp)
                                .padding(start = 26.dp, end = 26.dp, bottom = 90.dp)
                                .shadow(elevation = 8.dp, shape = RoundedCornerShape(20.dp)),
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.custom_list))
                        ) {

                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(
                                        start = 22.dp,
                                        end = 22.dp,
                                        top = 10.dp,
                                        bottom = 20.dp
                                    )
                            ) {

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(100.dp), verticalAlignment = Alignment.CenterVertically
                                ) {

                                    Image(
                                        painter = painterResource(id = getDrawableResource(app)),
                                        contentDescription = "",
                                        modifier = Modifier
                                            .size(64.dp)
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
                                                .weight(1f), contentAlignment = Alignment.BottomStart
                                        ) {
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                            ) {
                                                Text(
                                                    text = app,
                                                    color = colorResource(id = R.color.custom_text),
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 24.sp,
                                                )


                                                Spacer(modifier = Modifier.width(5.dp))
                                            }
                                        }

                                        Text(
                                            text = price,
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .weight(1f),
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 20.sp,
                                        )

                                    }

                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize(), contentAlignment = Alignment.CenterEnd
                                    ) {
                                        IconButton(onClick = {
                                            showDialog = true
                                            }
                                        ) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.ic_delete),
                                                contentDescription = "delete",
                                                tint = Color(0xFF333333)
                                            )
                                        }

                                        if (showDialog) {
                                            AlertDialog(
                                                onDismissRequest = {
                                                    showDialog = false
                                                },
                                                title = {
                                                    Text(text = "Delete Subscription")
                                                },
                                                text = {
                                                    Text(text = "Are you sure you want to delete?")
                                                },
                                                confirmButton = {
                                                    Button(
                                                        onClick = {
                                                            subViewmodel.deleteSubscription(subs)
                                                            subViewmodel.deleteUsage(subs.name)
                                                            navController.popBackStack()
                                                            showDialog = false
                                                        },
                                                        colors = buttonColors(containerColor = Color.Red)
                                                    ) {
                                                        Text(text = "Confirm")
                                                    }
                                                },
                                                dismissButton = {
                                                    Button(
                                                        onClick = {
                                                            showDialog = false
                                                        },
                                                        colors = buttonColors(containerColor = colorResource(id = R.color.custom_text_light)),
                                                    ) {
                                                        Text(text = "Cancel")
                                                    }
                                                }
                                            )
                                        }

                                    }

                                }

                                Row {
                                    Text(
                                        text = "Plan",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp,
                                        color = colorResource(id = R.color.custom_text)
                                    )
                                    Text(
                                        text = plan,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        color = colorResource(id = R.color.custom_text_light),
                                        textAlign = TextAlign.Right,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }

                                Row(modifier = Modifier.padding(top = 4.dp)) {
                                    Text(
                                        text = "Membership start date",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp,
                                        color = colorResource(id = R.color.custom_text)
                                    )
                                    Text(
                                        text = date,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        color = colorResource(id = R.color.custom_text_light),
                                        textAlign = TextAlign.Right,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }

                                Row(modifier = Modifier.padding(top = 4.dp)) {
                                    Text(
                                        text = "Payment method",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp,
                                        color = colorResource(id = R.color.custom_text)
                                    )
                                    Text(
                                        text = card,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        color = colorResource(id = R.color.custom_text_light),
                                        textAlign = TextAlign.Right,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }

                                if(!checkForUsagePermission(context)){
                                    Text(
                                        text = "Note", fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp,
                                        color = colorResource(id = R.color.custom_text),
                                        modifier = Modifier.padding(top = 22.dp)
                                    )

                                    Text(text = note)

                                    Row(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(bottom = 20.dp),
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.Bottom
                                    ) {
                                        IconButton(
                                            onClick = { navController.navigate(NavScreen.EditScreen.route + "/${subs.id}") },
                                            modifier = Modifier
                                                .clip(CircleShape)
                                                .background(Color(0xFF333333))
                                                .size(46.dp)
                                        ) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.ic_edit),
                                                contentDescription = "edit", tint = Color.White,
                                                modifier = Modifier.size(24.dp)
                                            )
                                        }
                                    }

                                } else {

                                    if (subs.type == "video") {

                                        Row(
                                            modifier = Modifier.padding(top = 12.dp)
                                        ) {
                                            Text(
                                                text = "Usage",
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 18.sp,
                                                color = colorResource(id = R.color.custom_text)
                                            )
                                        }

                                        val usageTakelast = getUsage.takeLast(3)

                                        for (usageList in usageTakelast){
                                            if (usageList.name == app) {
                                                Row(modifier = Modifier.padding(top = 4.dp)) {
                                                    Text(
                                                        text = usageList.month,
                                                        fontSize = 16.sp,
                                                        color = colorResource(id = R.color.custom_text_light)
                                                    )
                                                    Text(
                                                        text = usageList.usage + "hr",
                                                        fontSize = 16.sp,
                                                        color = colorResource(id = R.color.custom_text_light),
                                                        textAlign = TextAlign.Right,
                                                        modifier = Modifier.fillMaxWidth()
                                                    )
                                                }
                                            }
                                        }

                                        Row(
                                            modifier = Modifier.padding(top = 12.dp)
                                        ) {
                                            Text(
                                                text = "Price per usage",
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 18.sp,
                                                color = colorResource(id = R.color.custom_text)
                                            )
                                            Text(
                                                text = "THB/hr",
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 16.sp,
                                                color = colorResource(id = R.color.custom_text),
                                                textAlign = TextAlign.Right,
                                                modifier = Modifier.fillMaxWidth()
                                            )
                                        }

                                        for (usageList in usageTakelast){
                                            if (usageList.name == app) {
                                                Row(modifier = Modifier.padding(top = 4.dp)) {
                                                    Text(
                                                        text = usageList.month,
                                                        fontSize = 16.sp,
                                                        color = colorResource(id = R.color.custom_text_light)
                                                    )
                                                    Text(
                                                        text = usageList.usageprice,
                                                        fontSize = 16.sp,
                                                        color = colorResource(id = R.color.custom_text_light),
                                                        textAlign = TextAlign.Right,
                                                        modifier = Modifier.fillMaxWidth()
                                                    )
                                                }
                                            }
                                        }
                                    }
                                    Text(
                                        text = "Note", fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp,
                                        color = colorResource(id = R.color.custom_text),
                                        modifier = Modifier.padding(top = 22.dp)
                                    )

                                    Text(text = note,
                                        modifier = Modifier.padding())

                                    Row(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(bottom = 20.dp),
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.Bottom
                                    ) {
                                        IconButton(
                                            onClick = { navController.navigate(NavScreen.EditScreen.route + "/${subs.id}") },
                                            modifier = Modifier
                                                .clip(CircleShape)
                                                .background(Color(0xFF333333))
                                                .size(46.dp)
                                        ) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.ic_edit),
                                                contentDescription = "edit", tint = Color.White,
                                                modifier = Modifier.size(24.dp)
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
