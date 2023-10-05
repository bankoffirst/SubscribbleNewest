package com.example.subscribble.activities

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.subscribble.R
import com.example.subscribble.navbar.NavScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(/*navController: NavController*/) {

    val total = 0f
    val formattedTotal = String.format("%.2f", total)
    val videoPrice = 0f
    val formattedvideoPrice = String.format("%.2f", videoPrice)
    val musicPrice = 0f
    val formattedmusicPrice = String.format("%.2f", musicPrice)

    val haveStreaming = true

    Scaffold(
        topBar = {
            Text(
                text = "Home",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier.padding(start = 26.dp, top = 22.dp, bottom = 22.dp)
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
                    .height(200.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.cus_black)) //Custom Color
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
                        color = Color.White
                    )
                    Text(
                        text = "$formattedTotal THB",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color(0xFF0AA6EC),
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
                        .padding(top = 22.dp, start = 26.dp, end = 26.dp)
                        .weight(1f)
                ) {
                    Text(
                        text = "Video Streaming",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color(0xFF808080)
                    )
                    Text(
                        text = "$formattedvideoPrice THB",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color(0xFF808080),
                        textAlign = TextAlign.Right,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 26.dp, end = 26.dp)
                        .weight(1f)
                ) {
                    Text(
                        text = "Music Streaming",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color(0xFF808080)
                    )
                    Text(
                        text = "$formattedmusicPrice THB",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color(0xFF808080),
                        textAlign = TextAlign.Right,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }


            Text(
                text = "Your subscriptions",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 26.dp, top = 28.dp)
            )

            //Subscriptions

            Column(modifier = Modifier.fillMaxHeight()) {

                if (!haveStreaming) {    //Show add button when haveStreaming is false
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .padding(start = 20.dp, end = 20.dp, top = 20.dp)
                            .shadow(elevation = 8.dp, shape = RoundedCornerShape(20.dp))
                            .clickable { /*navController.navigate(NavScreen.AddSubscription.route)*/ },
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
                } else {   //Show subscriptions

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .padding(start = 20.dp, end = 20.dp, top = 20.dp)
                            .shadow(elevation = 8.dp, shape = RoundedCornerShape(20.dp))
                            .clickable { println("add subscription") },
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
                                painter = painterResource(id = R.drawable.netflix),
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


                                Box(modifier = Modifier
                                    .fillMaxSize()
                                    .weight(1f)
                                    ,contentAlignment = Alignment.BottomStart
                                ) {
                                        Row(modifier = Modifier
                                            .fillMaxWidth()) {
                                            Text(
                                                text = "Netflix",
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 18.sp,
                                            )

                                            Spacer(modifier = Modifier.width(5.dp))

                                            Box(
                                                modifier = Modifier
                                                    .size(10.dp)
                                                    .background(Color.Red, shape = CircleShape)
                                                    .align(Alignment.CenterVertically)
                                            )

                                        }
                                }
                                Text(
                                    text = "199/month", modifier = Modifier
                                        .fillMaxSize()
                                        .weight(1f),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,

                                )

                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(end = 20.dp)
                                ,contentAlignment = Alignment.CenterEnd
                            ){
                                Text(text = "5 Oct", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                            }

                        }
                    }

                }
            }

        }
    }
}


@Preview(showBackground = true, device = "spec:width=1440px,height=3088px,dpi=441")
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}