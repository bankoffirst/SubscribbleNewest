package com.example.subscribble.navbar

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.subscribble.activities.AddScreen
import com.example.subscribble.activities.DataVisualizationScreenScreen
import com.example.subscribble.activities.HomeScreen
import com.example.subscribble.activities.UpcomingBillsScreen

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
            HomeScreen()
        }
        composable(route = BottomBarScreen.Add.route)
        {
            AddScreen()
        }
        composable(route = BottomBarScreen.Data_visual.route)
        {
            DataVisualizationScreenScreen()
        }
        composable(route = BottomBarScreen.Bills.route)
        {
            UpcomingBillsScreen()
        }
    }
}

