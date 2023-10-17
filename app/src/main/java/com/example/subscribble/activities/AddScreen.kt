package com.example.subscribble.activities

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.subscribble.R
import com.example.subscribble.navbar.NavScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(navController: NavController){
    Scaffold(
        topBar = {
            Text(
                text = "Add",
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

            Column(modifier = Modifier. fillMaxHeight()) {

                Text(
                    text = "New Payment method",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(start = 26.dp, top = 28.dp),
                    color = colorResource(id = R.color.custom_text)
                )

                //Show add payment method
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .padding(start = 20.dp, end = 20.dp, top = 28.dp)
                    .shadow(elevation = 8.dp, shape = RoundedCornerShape(20.dp))
                    .clickable { navController.navigate(NavScreen.AddPayment.route) }, //To add card
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.custom_list))
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add icon",
                        tint = Color(0xFFD9D9D9),
                        modifier = Modifier
                            .size(40.dp)
                            .align(Alignment.CenterHorizontally)
                            .weight(1f)
                    )
                }

                //Show Streaming

                Text(
                    text = "New Subscription",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(start = 26.dp, top = 44.dp),
                    color = colorResource(id = R.color.custom_text)
                )

                //Show add subscription method
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .padding(start = 20.dp, end = 20.dp, top = 28.dp)
                    .shadow(elevation = 8.dp, shape = RoundedCornerShape(20.dp))
                    .clickable { navController.navigate(NavScreen.AddSubscription.route) }, //To add subscription
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add icon",
                        tint = Color(0xFFD9D9D9),
                        modifier = Modifier
                            .size(40.dp)
                            .align(Alignment.CenterHorizontally)
                            .weight(1f)
                    )
                }

            }

            Text(
                text = "New Subscription",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 28.dp, top = 28.dp)
            )

            Column(modifier = Modifier. fillMaxHeight()) {
                //Show add payment method
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .padding(start = 20.dp, end = 20.dp, top = 28.dp)
                    .shadow(elevation = 8.dp, shape = RoundedCornerShape(20.dp))
                    .clickable { println("add") }, //To add Screen!!!!!!!!!!!!
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add icon",
                        tint = Color(0xFFD9D9D9),
                        modifier = Modifier
                            .size(40.dp)
                            .align(Alignment.CenterHorizontally)
                            .weight(1f)
                    )
                }

                //Show Streaming

            }


        }
    }

}

//@Preview(showBackground = true, device = "spec:width=1440px,height=3088px,dpi=441")
//@Composable
//fun AddScreenPreview(){
//    AddScreen()
//}