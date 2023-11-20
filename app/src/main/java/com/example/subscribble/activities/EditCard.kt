package com.example.subscribble.activities

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButton
import androidx.compose.ui.draw.shadow
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.subscribble.R
import com.example.subscribble.database.module.SubscriptionViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditCard(navController: NavController, cardsId: Int, cardViewmodel:SubscriptionViewModel = hiltViewModel()) {

    val cards = cardViewmodel.cards.collectAsState(initial = emptyList())
    val subscription = cardViewmodel.subs.collectAsState(initial = emptyList())

    val card = cardViewmodel.getCardById(cardsId)

    card?.let { cards ->
        var name = cards.name
        var detail = cards.detail


        val alert = remember {
            mutableStateOf("")
        }

        val total = 0f
        val formattedTotal = String.format("%.2f", total)
        val videoPrice = 0f
        val formattedvideoPrice = String.format("%.2f", videoPrice)
        val musicPrice = 0f
        val formattedmusicPrice = String.format("%.2f", musicPrice)
        var textName by remember { mutableStateOf(name) }
        var textDetail by remember { mutableStateOf(detail) }

        Scaffold(
            topBar = {
                Row(
                    Modifier.padding(start = 26.dp, top = 22.dp, bottom = 22.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { navController.popBackStack() },
                        modifier = Modifier.size(24.dp)) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_back_ios),
                            contentDescription = "arrow back",
                            tint = colorResource(id = R.color.custom_text)
                        )
                    }
                    Text(
                        text = "AddPayment",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = colorResource(id = R.color.custom_text),
                        modifier = Modifier.padding(start = 2.dp)
                    )
                }
            }
        ) { contentPadding ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding)
            ) {
                Column(modifier = Modifier.fillMaxHeight()) {

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(750.dp)
                            .padding(start = 20.dp, end = 20.dp, top = 0.dp)
                            .shadow(elevation = 8.dp, shape = RoundedCornerShape(20.dp)),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                //.padding(start = 20.dp, end = 20.dp)
                                .shadow(
                                    elevation = 8.dp,
                                    shape = RoundedCornerShape(20.dp),
                                    spotColor = Color.LightGray
                                )
                                .height(200.dp),
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.Black)
                        ) {

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 22.dp, start = 26.dp, end = 26.dp)
                                    .weight(1f)
                            ) {
                                Text(
                                    text = textName, //card name
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

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 22.dp, start = 26.dp, end = 26.dp)
                                    .weight(1f)
                            ) {
                                Text(
                                    text = textDetail, // credit card number
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    color = Color.White
                                )
                            }

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
                            text = "Credit or Debit Name",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(start = 26.dp, top = 60.dp)
                        )

                        //textName

                        TextField(
                            value = textName,
                            onValueChange = { textName = it },
                            modifier = Modifier.padding(start = 26.dp, top = 10.dp),
                            placeholder = { Text(text = "Credit or Debit Name") },
                            shape = RectangleShape,
                            singleLine = true,
                            colors = TextFieldDefaults.textFieldColors(containerColor = Color.White),
                            maxLines = 1
                        )

                        Text(
                            text = "Details",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(start = 26.dp, top = 50.dp)
                        )

                        //textDetail

                        TextField(
                            value = textDetail,
                            onValueChange = { textDetail = it },
                            modifier = Modifier.padding(start = 26.dp, top = 10.dp),
                            placeholder = { Text(text = "Details of Card") },
                            shape = RectangleShape,
                            colors = TextFieldDefaults.textFieldColors(containerColor = Color.White),
                            maxLines = 1,
                            singleLine = true
                        )


                        Text(
                            text = alert.value,
                            color = Color.Red,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 25.dp)
                        )


                        //Icon Add

                        IconButton(
                            onClick = {
                                if (textName != "") {
                                    val updateCard = cards.copy(name = textName, detail = textDetail)
                                    cardViewmodel.updateCard(updateCard)
                                    for (subsList in subscription.value) {
                                        if (subsList.cardName == cards.name){
                                            subsList.cardName = textName
                                            cardViewmodel.updateSubscription(subsList)
                                        }
                                    }
                                    navController.popBackStack()

                                } else {
                                    alert.value = "Please Fill Card Name"
                                }
                                //println("Card Name : $textName. And Details : $textDetail")
                            },
                            content = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_save),
                                    contentDescription = "Add icon",
                                    tint = Color.Black,
                                    modifier = Modifier
                                        .size(50.dp)
                                )
                            },
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(top = 65.dp)
                                .clip(CircleShape)
                                .size(50.dp)
                        )


                    }


                }
            }

        }
    }
}