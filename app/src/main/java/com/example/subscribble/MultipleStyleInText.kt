package com.example.subscribble

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle

@Composable
fun PriceFormat(price: String): AnnotatedString {
    val text = buildAnnotatedString {
        withStyle(SpanStyle(color = colorResource(id = R.color.custom_text))) {
            append(price)
        }
        withStyle(SpanStyle(color = colorResource(id = R.color.custom_text_light))) {
            append("/month")
        }
    }

    return text
}