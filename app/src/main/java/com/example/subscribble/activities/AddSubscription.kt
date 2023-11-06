package com.example.subscribble.activities

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.subscribble.R
import com.example.subscribble.classify
import com.example.subscribble.database.SubsList
import com.example.subscribble.database.module.SubscriptionViewModel
import com.example.subscribble.navbar.BottomBarScreen
import com.example.subscribble.navbar.NavScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddSubscription(navController: NavController, subViewmodel: SubscriptionViewModel = hiltViewModel()) {

    val subscriptions = subViewmodel.subs.collectAsState(initial = emptyList())

    val cards = subViewmodel.cards.collectAsState(initial = emptyList())

    val listItems = arrayOf("Netflix","Spotify","DisneyPlus","Youtube","AppleTV","AppleMusic")

    // state of menu
    var expanded by remember {
        mutableStateOf(false)
    }

    // remember selected item
    var selectedItem by remember {
        mutableStateOf(listItems[0])
    }

    var textPlan by remember { mutableStateOf("") }
    var numPrice by remember { mutableStateOf("") }
    var textNote by remember { mutableStateOf("") }

    val calendar = Calendar.getInstance()
    val context = LocalContext.current
    val selectYear = calendar[Calendar.YEAR]
    val selectMonth = calendar[Calendar.MONTH]
    val selectDay = calendar[Calendar.DAY_OF_MONTH]
    val selectDate = remember { mutableStateOf("")}

    val dateToday = DatePickerDialog(context, R.style.DatePickerTheme,
        {_: DatePicker, selectYear:Int, selectMonth:Int, selectDay:Int ->
            selectDate.value = "$selectDay/${selectMonth +1}/$selectYear"
        }, selectYear,selectMonth,selectDay
    )

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
                    text = "AddSubscription",
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
                ){

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 15.dp, start = 26.dp, end = 15.dp)
                    ) {
                        Text(
                            text = "Select Your Subscription",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = Color.Black
                        )

                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 15.dp, start = 26.dp, end = 15.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.Top)
                        ) {
                            ExposedDropdownMenuBox(
                                expanded = expanded,
                                onExpandedChange = {
                                    expanded = !expanded
                                }
                            ) {
                                OutlinedTextField(
                                    value = selectedItem,
                                    onValueChange = {},
                                    readOnly = true,
                                    trailingIcon = {
                                        ExposedDropdownMenuDefaults.TrailingIcon(
                                            expanded = expanded
                                        )
                                    },
                                    modifier = Modifier
                                        .menuAnchor(),
                                    textStyle = TextStyle(fontSize = 18.sp)
                                )

                                ExposedDropdownMenu(
                                    expanded = expanded,
                                    onDismissRequest = { expanded = false },
                                    modifier = Modifier.background(color = Color.White)
                                ) {
                                    listItems.forEach { item ->
                                        DropdownMenuItem(
                                            text = { Text(text = item) },
                                            onClick = {
                                                selectedItem = item
                                                expanded = false
                                            },
                                            modifier = Modifier.background(color = Color.White)
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Text(
                        text = "Plan",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(start = 26.dp, top = 15.dp)
                    )

                    TextField(
                        value = textPlan,
                        onValueChange = { textPlan = it },
                        modifier = Modifier
                            .padding(start = 26.dp)
                            .width(170.dp),
                        placeholder = { Text(text = "Subscription Plan")},
                        shape = RectangleShape,
                        colors = TextFieldDefaults.textFieldColors(containerColor = Color.White),
                        maxLines = 1,
                        singleLine = true,
                        textStyle = TextStyle(fontSize = 18.sp)
                    )

                    Text(
                        text = "Price",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(start = 26.dp, top = 15.dp)
                    )

                    Row(modifier = Modifier.fillMaxWidth()) {
                        TextField(
                            value = numPrice,
                            onValueChange = { numPrice = it },
                            modifier = Modifier
                                .padding(start = 26.dp)
                                .width(160.dp),
                            placeholder = { Text(text = "Price per month") },
                            shape = RectangleShape,
                            colors = TextFieldDefaults.textFieldColors(containerColor = Color.White),
                            maxLines = 1,
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            textStyle = TextStyle(fontSize = 18.sp)
                        )
                        Text(
                            text = "/Month",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(top = 15.dp)
                        )
                    }



                    Text(
                        text = "Membership Start Date",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(start = 26.dp, top = 15.dp)
                    )

                    // button select date
                    Row(modifier = Modifier.fillMaxWidth()){
                        OutlinedButton(onClick = { dateToday.show() },
                            Modifier
                                .padding(start = 26.dp)
                                .background(Color.White)

                        ) {
                            if (selectDate.value.isNotEmpty()){
                                Text(
                                    text = "${selectDate.value}",
                                    fontSize = 18.sp,
                                    color = Color.Black
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Default.DateRange,
                                    contentDescription = null,
                                    tint = Color.Black
                                )

                            }
                        }
                    }

                    Text(
                        text = "Payment Method",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(start = 26.dp, top = 15.dp)
                    )

                    if (cards.value.isEmpty()) {
                        IconButton(
                            onClick = { navController.navigate(NavScreen.AddPayment.route) },
                            content = {
                                Icon(
                                    imageVector = Icons.Default.AddCircle,
                                    contentDescription = "Add icon",
                                    tint = Color.Black,
                                    modifier = Modifier
                                        .size(35.dp)
                                )
                            },
                            modifier = Modifier
                                .padding(start = 26.dp, top = 10.dp)
                                .clip(CircleShape)
                                .size(35.dp)
                        )
                    } else {
                        LazyColumn(modifier = Modifier
                            .padding(top = 28.dp, bottom = 40.dp)
                            .fillMaxHeight()
                        ){
                            items(cards.value){cardsList ->
                                OutlinedButton(onClick = { /*TODO*/ }) {
                                    
                                }
                            }
                        }
                    }

                    Text(
                        text = "Note",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(start = 26.dp, top = 15.dp)
                    )

                    OutlinedTextField(
                        value = textNote,
                        onValueChange = { textNote = it },
                        modifier = Modifier
                            .padding(start = 26.dp, top = 10.dp, end = 26.dp)
                            .fillMaxWidth(),
                        colors = TextFieldDefaults.textFieldColors(containerColor = Color.White),
                        textStyle = TextStyle(fontSize = 18.sp)

                    )

                    //Icon Add

                    IconButton(
                        onClick = {
                                  if (selectedItem != "" && numPrice != "" && selectDate.value != ""){
                                      subViewmodel.insertSub(
                                          SubsList(
                                              name = selectedItem,
                                              planName = textPlan,
                                              price = numPrice.toFloat(),
                                              date = selectDate.value,
                                              note = textNote,
                                              type = classify(selectedItem)
                                          )
                                      ); navController.navigate(BottomBarScreen.Home.route)
                                  //println("Item Name : $selectedItem. And expanded : $expanded, plan : $textPlan, Start Date : ${selectDate.value}")
                                  } else {
                                      println("Error!")
                                  }
                            //navController.navigate(BottomBarScreen.Home.route)
                        },
                        content = {
                            Icon(
                                imageVector = Icons.Default.AddCircle,
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


//@Preview(showBackground = true, device = "spec:width=1440px,height=3088px,dpi=441")
//@Composable
//fun NewSubscriptionPreview() {
//    AddSubscription()
//}