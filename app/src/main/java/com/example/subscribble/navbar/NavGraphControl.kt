package com.example.subscribble.navbar

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.subscribble.activities.AddPayment
import com.example.subscribble.activities.AddScreen
import com.example.subscribble.activities.AddSubscription
import com.example.subscribble.activities.DataVisualizationScreenScreen
import com.example.subscribble.activities.HomeScreen
import com.example.subscribble.activities.ShowDetailScreen
import com.example.subscribble.activities.UpcomingBillsScreen
import com.example.subscribble.database.SubsList
import com.example.subscribble.database.module.SubscriptionViewModel

@Composable
fun NavGraphControl(
    navController: NavController
){
    NavHost(
        navController = navController as NavHostController,
        startDestination = BottomBarScreen.Home.route
    ){
        composable(route = BottomBarScreen.Home.route)
        {
            HomeScreen(navController = navController)
        }
        composable(route = BottomBarScreen.Add.route)
        {
            AddScreen(navController = navController)
        }
        composable(route = BottomBarScreen.Data_visual.route)
        {
            DataVisualizationScreenScreen()
        }
        composable(route = BottomBarScreen.Bills.route)
        {
            UpcomingBillsScreen()
        }
        composable(route = NavScreen.AddPayment.route)
        {
            AddPayment(navController = navController)
        }
        composable(route = NavScreen.AddSubscription.route)
        {
            AddSubscription(navController = navController)
        }
        composable(route = NavScreen.ShowDetailScreen.route)
        {
            ShowDetailScreen()
        }
    }
}

