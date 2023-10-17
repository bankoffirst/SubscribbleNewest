package com.example.subscribble.activities

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.swipeable
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.navigation.NavHostController
import com.example.subscribble.PriceFormat
import com.example.subscribble.R
import com.example.subscribble.database.module.SubscriptionViewModel
import com.example.subscribble.getDrawableResource
import com.example.subscribble.navbar.NavScreen
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@Composable
fun SubscriptionList(
    id:Int, name:String, price:Int,
    subViewmodel: SubscriptionViewModel = hiltViewModel()) {

    val subscription = subViewmodel.subs.collectAsState(initial = emptyList())

//    val swipeDelete = SwipeAction(
//        onSwipe = {
//            subViewmodel.deleteSub(id)
//            Log.d("$id","Delete")
//        },
//        icon = {
//            Icon(modifier = Modifier.padding(16.dp),
//                painter = painterResource(id = R.drawable.ic_delete),
//                contentDescription = null,
//                tint = Color.Unspecified)
//        },
//        background = Color.Red
//    )

    //SwipeableActionsBox(swipeThreshold = 50.dp, endActions = listOf(swipeDelete)) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(start = 20.dp, end = 20.dp, bottom = 10.dp)
                .shadow(elevation = 8.dp, shape = RoundedCornerShape(15.dp))
                .clickable { /*navController.navigate(NavScreen.ShowDetailScreen.route)*/ },

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
                    painter = painterResource(id = getDrawableResource(name)),
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
                                text = name,
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
                        text = PriceFormat(price = price.toString()),
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,

                        )

                }
            }
        }

    }
//}

