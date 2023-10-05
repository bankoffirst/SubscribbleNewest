package com.example.subscribble.navbar

import com.example.subscribble.R

sealed class NavScreen(
    val route: String
){
    // for screen newPayment
    object AddPayment: NavScreen(
        route = "AddPayment"
    )

    // for screen Add
    object AddSubscription : NavScreen(
        route = "AddSubscription"
    )
}