package com.example.subscribble.activities

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.input.pointer.pointerInput

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, subViewmodel: SubscriptionViewModel = hiltViewModel()) {

    val subscription = subViewmodel.subs.collectAsState(initial = emptyList())

    val cards = subViewmodel.cards.collectAsState(initial = emptyList())

    LaunchedEffect(key1 = subViewmodel){
        subViewmodel.loadSubs()
        subViewmodel.loadCards()
    }

    val sumPriceMusic by remember { mutableStateOf(subViewmodel.sumPriceByCategory("music")) }
    val sumPriceVideo by remember { mutableStateOf(subViewmodel.sumPriceByCategory("video")) }
    val total by remember { mutableStateOf(sumPriceMusic + sumPriceVideo) }

    var expanded by remember{ mutableStateOf(false) }
    var selectedCard by remember { mutableStateOf("Total Price") }

    var formattedTotal by remember { mutableStateOf(String.format("%.2f", total)) }
    var formattedVideoPrice by remember { mutableStateOf(String.format("%.2f", sumPriceVideo)) }
    var formattedMusicPrice by remember { mutableStateOf(String.format("%.2f", sumPriceMusic)) }

    val cardList = mutableListOf("Total Price")
    cards.value.forEach { card ->
        val getCards = listOf(card.name)
        cardList.addAll(getCards)
    }

    var showCardDelete by remember { mutableStateOf(false) }

    if (showCardDelete){
        cards.value.forEach {cardsList ->
            if (selectedCard == cardsList.name){
                AlertDialog(
                    onDismissRequest = { showCardDelete = false },
                    icon = { Icon(painter = painterResource(id = R.drawable.ic_delete), contentDescription = "delete") },
                    title = { Text(text = "Delete") },
                    text = { Text(text = "Do you want to delete \"${cardsList.name}\"? If you delete it,\nyou will not be able to recover the data.") },
                    dismissButton = {
                        TextButton(onClick = {
                            showCardDelete = false
                        }) {
                            Text(text = "Cancel")
                        }
                    },
                    confirmButton = {
                        TextButton(onClick = {
                            //ลบ Card
                            //showCardDelete = false
                            subViewmodel.deleteCard(cardsList)
                            subscription.value.forEach { subsList ->
                                if (subsList.cardName == cardsList.name){
                                    subsList.cardName = ""
                                    subViewmodel.updateSubscription(subsList)
                                    println("Updated to EvilDemon")

                                }
                            }
                            selectedCard = "Total Price"
                            showCardDelete = false

                        }) {
                            Text(text = "Confirm")
                        }
                    }
                )
            }
        }
//        AlertDialog(
//            onDismissRequest = { showCardDelete = false },
//            icon = { Icon(painter = painterResource(id = R.drawable.ic_delete), contentDescription = "delete") },
//            title = { Text(text = "Delete") },
//            text = { Text(text = "Do you want to delete \"$cardToDelete\"? If you delete it,\nyou will not be able to recover the data.") },
//            dismissButton = {
//                TextButton(onClick = {
//                    showCardDelete = false
//                }) {
//                    Text(text = "Cancel")
//                }
//            },
//            confirmButton = {
//                TextButton(onClick = {
//                    //ลบ Card
//                    showCardDelete = false
//                }) {
//                    Text(text = "Confirm")
//                }
//            }
//        )
    }

    var showCardEdit by remember { mutableStateOf(false) }

    if (showCardEdit){
        cards.value.forEach { cardsList ->
            if (selectedCard == cardsList.name){
                AlertDialog(
                    onDismissRequest = { showCardEdit = false },
                    icon = { Icon(painter = painterResource(id = R.drawable.ic_edit), contentDescription = "delete") },
                    title = { Text(text = "Edit") },
                    text = { Text(text = "Do you want to edit \"${cardsList.name}\" ?") },
                    dismissButton = {
                        TextButton(onClick = {
                            showCardEdit = false
                        }) {
                            Text(text = "Cancel")
                        }
                    },
                    confirmButton = {
                        TextButton(onClick = {
                            navController.navigate(NavScreen.EditCard.route + "/${cardsList.id}")
                        }) {
                            Text(text = "Edit")
                        }
                    }
                )
            }

        }
//        AlertDialog(
//            onDismissRequest = { showCardEdit = false },
//            icon = { Icon(painter = painterResource(id = R.drawable.ic_edit), contentDescription = "delete") },
//            title = { Text(text = "Edit") },
//            text = { Text(text = "Do you want to edit \"$cardToDelete\" ?") },
//            dismissButton = {
//                TextButton(onClick = {
//                    showCardEdit = false
//                }) {
//                    Text(text = "Cancel")
//                }
//            },
//            confirmButton = {
//                TextButton(onClick = {
//                    showCardEdit = false
//                }) {
//                    Text(text = "Edit")
//                }
//            }
//        )
    }


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

            if (selectedCard == "Total Price") {
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
                            text = "$formattedTotal THB",
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
                                text = "$formattedVideoPrice THB",
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
                                text = "$formattedMusicPrice THB",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = colorResource(id = R.color.custom_card_subtext),
                                textAlign = TextAlign.Right,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            } else {

                formattedMusicPrice = "0.00"
                formattedVideoPrice = "0.00"
                formattedTotal = "0.00"

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp)
                        .height(200.dp)
                        .shadow(elevation = 8.dp, shape = RoundedCornerShape(20.dp))
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onLongPress = {
//                               expandedCardMenu.value = true
                                    showCardDelete = true
                                    }
                                    , onDoubleTap = {
                                        //ไปหน้า Edit
                                        showCardEdit = true
//                                showCardMenu = true
                                    }
                                )
                            }
                        ,
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.custom_card)) //Custom Color
                    ){
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
                                text = "$formattedTotal THB",
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
                                    text = "$formattedVideoPrice THB",
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
                                    text = "$formattedMusicPrice THB",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = colorResource(id = R.color.custom_card_subtext),
                                    textAlign = TextAlign.Right,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    }

            }


                Row(modifier = Modifier
                    .padding(start = 26.dp, end = 26.dp, top = 10.dp)
                    .fillMaxWidth()
                    , verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Your subscriptions",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = colorResource(id = R.color.custom_text),
                    )

                    Box(modifier = Modifier.fillMaxWidth()) {
                        TextButton(onClick = { expanded = true }, modifier = Modifier.align(Alignment.CenterEnd)) {
                            Text(text = selectedCard,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp,
                                color = colorResource(id = R.color.custom_card_total))

                            Box(modifier = Modifier.padding(top = 40.dp)) {
                                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false },
                                    modifier = Modifier
                                        .background(Color.White)) {
                                    cardList.forEach{
                                        DropdownMenuItem(onClick = {
                                            expanded = false
                                            selectedCard = it
                                        },text = {Text(it, color = colorResource(id = R.color.custom_text_light))})
                                    }
                                }
                            }
                        }
                    }

                }


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
                                if (selectedCard == subsList.cardName) {

                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(100.dp)
                                            .padding(start = 20.dp, end = 20.dp, bottom = 10.dp)
                                            .shadow(
                                                elevation = 8.dp,
                                                shape = RoundedCornerShape(15.dp)
                                            )
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

                                    val categorySum = subscription.value
                                        .filter { it.cardName == subsList.cardName}
                                        .sumOf { it.price.toDouble() }

                                    val musicSum = subscription.value
                                        .filter { it.type == "music" && it.cardName == subsList.cardName }
                                        .sumOf { it.price.toDouble() }

                                    val videoSum = subscription.value
                                        .filter { it.type == "video" && it.cardName == subsList.cardName }
                                        .sumOf { it.price.toDouble() }

                                    formattedMusicPrice = String.format("%.2f", musicSum.toFloat())
                                    formattedVideoPrice = String.format("%.2f", videoSum.toFloat())
                                    formattedTotal = String.format("%.2f", categorySum.toFloat())


                                } else if (selectedCard == "Total Price") {
                                    formattedVideoPrice = String.format("%.2f", sumPriceVideo)
                                    formattedMusicPrice = String.format("%.2f", sumPriceMusic)
                                    formattedTotal = String.format("%.2f", sumPriceMusic + sumPriceVideo)
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(100.dp)
                                            .padding(start = 20.dp, end = 20.dp, bottom = 10.dp)
                                            .shadow(
                                                elevation = 8.dp,
                                                shape = RoundedCornerShape(15.dp)
                                            )
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

                                }

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