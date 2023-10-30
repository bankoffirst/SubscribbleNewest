package com.example.subscribble.activities

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.navigation.compose.rememberNavController
import com.example.subscribble.PriceFormat
import com.example.subscribble.R
import com.example.subscribble.database.module.SubscriptionViewModel
import com.example.subscribble.getDrawableResource
import com.example.subscribble.navbar.NavScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, subViewmodel: SubscriptionViewModel = hiltViewModel()) {


    //val haveStreaming = true
    val subscription = subViewmodel.subs.collectAsState(initial = emptyList())

    LaunchedEffect(key1 = subViewmodel){
        subViewmodel.loadSubs()
    }

    val sumPriceMusic = subViewmodel.sumPriceByCategory("music")
    val sumPriceVideo = subViewmodel.sumPriceByCategory("video")
    val total = sumPriceMusic + sumPriceVideo

    val formattedTotal = String.format("%.2f", total)
    val formattedvideoPrice = String.format("%.2f", sumPriceVideo)
    val formattedmusicPrice = String.format("%.2f", sumPriceMusic)

    Scaffold(
        topBar = {
            Text(
                text = "Home",
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

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp)
                    .height(200.dp)
                    .shadow(elevation = 8.dp, shape = RoundedCornerShape(20.dp)),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.custom_card)) //Custom Color
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 22.dp, start = 26.dp, end = 26.dp)
                        .weight(1f)
                ) {
                    Text(
                        text = "Total Price",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = colorResource(id = R.color.custom_card_text)
                    )
                    Text(
                        text = "${formattedTotal} THB",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = colorResource(id = R.color.custom_card_total),
                        textAlign = TextAlign.Right,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .fillMaxHeight()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 22.dp, start = 26.dp, end = 26.dp)
                            .weight(1f)
                    ) {
                        Text(
                            text = "Video Streaming",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = colorResource(id = R.color.custom_card_subtext)
                        )
                        Text(
                            text = "$formattedvideoPrice THB",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = colorResource(id = R.color.custom_card_subtext),
                            textAlign = TextAlign.Right,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.5f)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 26.dp, end = 26.dp, bottom = 22.dp)
                            .weight(1f)
                    ) {
                        Text(
                            text = "Music Streaming",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = colorResource(id = R.color.custom_card_subtext)
                        )
                        Text(
                            text = "$formattedmusicPrice THB",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = colorResource(id = R.color.custom_card_subtext),
                            textAlign = TextAlign.Right,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }


                Text(
                    text = "Your subscriptions",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(start = 26.dp, top = 28.dp),
                    color = colorResource(id = R.color.custom_text)
                )

                //Subscriptions

                Column(modifier = Modifier.fillMaxHeight()) {

                    if (subscription.value.isEmpty()) {    //Show add button when haveStreaming is false
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .padding(start = 20.dp, end = 20.dp, top = 20.dp)
                                .shadow(elevation = 8.dp, shape = RoundedCornerShape(20.dp))
                                .clickable { navController.navigate(NavScreen.AddSubscription.route) },
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add icon",
                                tint = Color(0xFFD9D9D9),
                                modifier = Modifier
                                    .size(50.dp)
                                    .align(Alignment.CenterHorizontally)
                                    .weight(1f)
                            )
                        }
                    } else {

                        LazyColumn(modifier = Modifier
                            .padding(top = 28.dp, bottom = 40.dp)
                            .fillMaxHeight()
                            )
                        {
                            items(subscription.value) { subsList ->
                                //SubscriptionList(subsList.id, subsList.name, subsList.price,)
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

//                                                    Box(
//                                                        modifier = Modifier
//                                                            .size(10.dp)
//                                                            .background(
//                                                                Color.Red,
//                                                                shape = CircleShape
//                                                            )
//                                                            .align(Alignment.CenterVertically)
//                                                    )

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


//                                Card(
//                                    modifier = Modifier
//                                        .fillMaxWidth()
//                                        .height(100.dp)
//                                        .padding(start = 20.dp, end = 20.dp, top = 20.dp)
//                                        .shadow(elevation = 8.dp, shape = RoundedCornerShape(15.dp))
//                                        .clickable { },
//                                    shape = RoundedCornerShape(20.dp),
//                                    colors = CardDefaults.cardColors(containerColor = Color.White)
//                                ) {
//                                    Row(
//                                        modifier = Modifier
//                                            .fillMaxSize()
//                                            .padding(start = 10.dp),
//                                        verticalAlignment = Alignment.CenterVertically
//                                    ) {
//
//                                        Image(
//                                            painter = painterResource(id = getDrawableResource(subsList.name)),
//                                            contentDescription = "",
//                                            modifier = Modifier
//                                                .size(60.dp)
//                                                .clip(RoundedCornerShape(20.dp))
//                                        )
//
//                                        Column(
//                                            modifier = Modifier
//                                                .width(150.dp)
//                                                .padding(start = 10.dp),
//                                        ) {
//
//                                            Box(
//                                                modifier = Modifier
//                                                    .fillMaxSize()
//                                                    .weight(1f),
//                                                contentAlignment = Alignment.BottomStart
//                                            ) {
//                                                Row(
//                                                    modifier = Modifier
//                                                        .fillMaxWidth()
//                                                ) {
//                                                    Text(
//                                                        text = subsList.name,
//                                                        fontWeight = FontWeight.Bold,
//                                                        fontSize = 18.sp,
//                                                        color = colorResource(id = R.color.custom_text),
//                                                    )
//
//                                                    Spacer(modifier = Modifier.width(5.dp))
//
////                                                    Box(
////                                                        modifier = Modifier
////                                                            .size(10.dp)
////                                                            .background(
////                                                                Color.Red,
////                                                                shape = CircleShape
////                                                            )
////                                                            .align(Alignment.CenterVertically)
////                                                    )
//
//                                                }
//                                            }
//                                            Text(
//                                                text = "${subsList.price}/month",
//                                                modifier = Modifier
//                                                    .fillMaxSize()
//                                                    .weight(1f),
//                                                fontWeight = FontWeight.Bold,
//                                                fontSize = 16.sp,
//                                                color = colorResource(id = R.color.custom_text),
//
//                                                )
//
//                                        }
//                                    }
//                                }
                            }




                        }
                    }

                }
            }
        }
    }



//@Preview(showBackground = true, device = "spec:width=1440px,height=3088px,dpi=441")
//@Composable
//fun HomeScreenPreview() {
//    HomeScreen()
//}