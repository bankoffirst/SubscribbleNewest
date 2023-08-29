package com.example.subscribble.activities

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DataVisualizationScreenScreen(){
    Column(modifier = Modifier.fillMaxSize()) {

        Row(modifier = Modifier.fillMaxWidth()) {

            Text(text = "Data Visualization", fontWeight = FontWeight.Bold, fontSize = 28.sp, modifier = Modifier
                .padding(start = 26.dp, top = 22.dp))
        }



    }

}

@Preview(showBackground = true, widthDp = 480, heightDp = 1030)
@Composable
fun DataVisualizationScreenScreenPreview(){
    DataVisualizationScreenScreen()
}