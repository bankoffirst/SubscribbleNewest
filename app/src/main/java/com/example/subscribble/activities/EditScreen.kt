package com.example.subscribble.activities

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.widget.DatePicker
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
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
import com.example.subscribble.PriceFormat
import com.example.subscribble.R
import com.example.subscribble.database.module.SubscriptionViewModel
import com.example.subscribble.getDrawableResource
import com.example.subscribble.navbar.NavScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreen(navController: NavController, subsId: Int,subViewmodel: SubscriptionViewModel = hiltViewModel()) {

    val subscription = subViewmodel.getSubscriptionById(subsId)

    subscription?.let { subs ->
        val app = subs.name
        var price = PriceFormat(subs.price.toString())
        var plan = subs.planName
        var date = subs.date
        var note = subs.note

        var planText by remember { mutableStateOf(plan)}
        var priceText by remember { mutableStateOf(subs.price.toString()) }
        var noteText by remember { mutableStateOf(note)}
        val selectDate = remember { mutableStateOf(date) }

        val calendar = Calendar.getInstance()
        val context = LocalContext.current
        val selectYear = calendar[Calendar.YEAR]
        val selectMonth = calendar[Calendar.MONTH]
        val selectDay = calendar[Calendar.DAY_OF_MONTH]

        val dateToday = DatePickerDialog(
            context, R.style.DatePickerTheme,
            { _: DatePicker, selectYear: Int, selectMonth: Int, selectDay: Int ->
                selectDate.value = "$selectDay/${selectMonth + 1}/$selectYear"
            }, selectYear, selectMonth, selectDay
        )

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
                        text = "EditScreen",
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

                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 26.dp, end = 26.dp, bottom = 90.dp)
                        .shadow(elevation = 8.dp, shape = RoundedCornerShape(20.dp)),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.custom_list))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 22.dp, end = 22.dp, top = 10.dp, bottom = 20.dp)
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
                                        .weight(1f), contentAlignment = Alignment.Center
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    ) {
                                        Text(
                                            text = app,
                                            color = colorResource(id = R.color.custom_text),
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 24.sp
                                        )

                                    }
                                }

                            }

                        }

                        Text(
                            text = "Plan",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = colorResource(id = R.color.custom_text)
                        )

                        TextField(
                            value = planText,
                            onValueChange = { planText = it },
                            modifier = Modifier
                                .width(170.dp),
                            placeholder = { Text(text = "Subscription Plan") },
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
                            modifier = Modifier.padding(top = 20.dp)
                        )

                        Row(modifier = Modifier.fillMaxWidth()) {
                            TextField(
                                value = priceText,
                                onValueChange = { priceText = it },
                                modifier = Modifier
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
                                text = "/month",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                modifier = Modifier.padding(top = 20.dp)

                            )
                        }

                        Text(
                            text = "Membership Start Date",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(top = 20.dp)

                        )

                        // button select date
                        Row(modifier = Modifier.fillMaxWidth()) {
                            OutlinedButton(
                                onClick = { dateToday.show() },
                                Modifier
                                    .background(Color.White)
                                    .padding(top = 10.dp)

                            ) {
                                Text(
                                    text = "${selectDate.value}",
                                    fontSize = 18.sp,
                                    color = Color.Black
                                )
                            }
                        }

                        Text(
                            text = "Payment Method",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(top = 20.dp)
                        )

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
                                .padding(top = 10.dp)
                                .clip(CircleShape)
                                .size(35.dp)
                        )

                        Text(
                            text = "Note",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(top = 20.dp)
                        )

                        OutlinedTextField(
                            value = noteText,
                            onValueChange = { noteText = it },
                            modifier = Modifier
                                .padding(top = 10.dp)
                                .fillMaxWidth(),
                            colors = TextFieldDefaults.textFieldColors(containerColor = Color.White),
                            textStyle = TextStyle(fontSize = 18.sp)

                        )

                        Row(modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 20.dp)
                            , horizontalArrangement = Arrangement.Center
                            , verticalAlignment = Alignment.Bottom) {
                            IconButton(onClick = {
                                val updateSubs = subs.copy(planName = planText, price = priceText.toFloat(), date = selectDate.value, note = noteText)
                                subViewmodel.updateSubscription(updateSubs)
                                navController.popBackStack()},
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(Color(0xFF333333))
                                    .size(46.dp)) {
                                Icon(painter = painterResource(id = R.drawable.ic_save),
                                    contentDescription = "save", tint = Color.White,
                                    modifier = Modifier.size(24.dp))
                            }
                        }


                    }
                }
            }
        }
    }
}

