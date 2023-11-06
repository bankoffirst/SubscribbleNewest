package com.example.subscribble.navbar

import com.example.subscribble.R

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: Int,
){
    //for home screen
    object Home: BottomBarScreen(
        route = "home",
        title = "Home",
        icon = R.drawable.ic_home,
        )

    //for add screen
    object Add: BottomBarScreen(
        route = "add",
        title = "Add",
        icon = R.drawable.ic_add,
    )

    //for data_visual screen
    object Data_visual: BottomBarScreen(
        route = "data_visual",
        title = "Data Visualization",
        icon = R.drawable.ic_data_visual,
    )

    //for UpcomingBills screen
    object Bills: BottomBarScreen(
        route = "bills",
        title = "Bills",
        icon = R.drawable.ic_receipt,
    )

}
