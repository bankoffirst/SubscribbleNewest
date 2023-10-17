package com.example.subscribble.activities

import androidx.compose.foundation.Image
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.subscribble.PriceFormat
import com.example.subscribble.R
import com.example.subscribble.getDrawableResource


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowDetailScreen(/*navController: NavController*/) {
    val app = "Netflix"
    val price = PriceFormat("199")
    val plan = "Basic"
    val date = "16/2/2023"
    val note = "lorem ipsum dolor sit amet consectetur adipisicing elit sed do eiusmod tempor incididunt ut labore et dolore magna aliqua obcaecati ipsa assumenda doloremque ipsam eos cupiditate libero fuga provident nemo architecto deserunt magnam praesentium natus impedit tenetur dolor pariatur suscipit ab sunt aspernatur vel ipsum necessitatibus voluptatum mollitia odio eligendi"

    Scaffold(
        topBar = {
            Row(Modifier.padding(start = 26.dp, top = 22.dp, bottom = 22.dp), verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { /*TODO*/ }, modifier = Modifier.size(24.dp)){
                    Icon(painter = painterResource(id = R.drawable.ic_arrow_back_ios),
                        contentDescription = "arrow back",
                        tint = colorResource(id = R.color.custom_text))}
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {

            Card(modifier = Modifier
                .fillMaxSize()
                .padding(start = 26.dp, end = 26.dp, bottom = 90.dp)
                .shadow(elevation = 8.dp, shape = RoundedCornerShape(20.dp)),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.custom_list))
            ) {

                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 22.dp, end = 22.dp, top = 10.dp, bottom = 20.dp)
                ) {

                    Row( modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),verticalAlignment = Alignment.CenterVertically) {

                        Image(
                            painter = painterResource(id = getDrawableResource(app)),
                            contentDescription = "",
                            modifier = Modifier
                                .size(80.dp)
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
                                        text = app,
                                        color = colorResource(id = R.color.custom_text),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 24.sp,
                                    )


                                    Spacer(modifier = Modifier.width(5.dp))
                                }
                            }
                            Text(

                                text = price, modifier = Modifier
                                    .fillMaxSize()
                                    .weight(1f),
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,

                                )

                        }

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                            ,contentAlignment = Alignment.CenterEnd
                        ){
                            IconButton(onClick = { /*TODO*/ }) {
                                Icon(painter = painterResource(id = R.drawable.ic_delete), contentDescription = "delete",tint = Color(0xFF333333))
                            }
                        }

                    }

                    Row(modifier = Modifier.padding(top = 22.dp)){
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

                    Row(modifier = Modifier.padding(top = 4.dp)){
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

                    Text(
                        text = "Usage", fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = colorResource(id = R.color.custom_text),
                        modifier = Modifier.padding(top = 22.dp)
                    )

                    Text(
                        text = "Price per usage", fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = colorResource(id = R.color.custom_text),
                        modifier = Modifier.padding(top = 22.dp)
                    )

                    Text(
                        text = "Note", fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = colorResource(id = R.color.custom_text),
                        modifier = Modifier.padding(top = 22.dp)
                    )

                    Text(text = note)



                }

            }

        }
    }
}


@Preview(showBackground = true, device = "spec:width=1440px,height=3088px,dpi=441")
@Composable
fun ShowDetailPreview() {
    ShowDetailScreen()
}