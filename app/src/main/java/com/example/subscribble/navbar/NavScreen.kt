package com.example.subscribble.navbar


sealed class NavScreen(
    val route: String
){
    object AddPayment: NavScreen(
        route = "AddPayment"
    )

    object AddSubscription : NavScreen(
        route = "AddSubscription"
    )

    object ShowDetailScreen : NavScreen(
        route = "ShowDetailScreen"
    )

    object EditScreen : NavScreen(
        route = "EditScreen"
    )

    object  EditCard : NavScreen(
        route = "EditCard"
    )

    object VideoDonut : NavScreen(
        route = "VideoDonut"
    )
    object MusicDonut : NavScreen(
        route = "MusicDonut"
    )
    object TotalLine : NavScreen(
        route = "TotalLine"
    )
    object TotalMocup : NavScreen(
        route = "TotalMocup"
    )

    object DayLine : NavScreen(
        route = "DayLine"
    )

    object DayMocup : NavScreen(
        route = "DayMocup"
    )
}