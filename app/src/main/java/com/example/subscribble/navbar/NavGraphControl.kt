package com.example.subscribble.navbar

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.subscribble.activities.AddPayment
import com.example.subscribble.activities.AddScreen
import com.example.subscribble.activities.AddSubscription
import com.example.subscribble.activities.DataVisualizationScreen
import com.example.subscribble.activities.EditScreen
import com.example.subscribble.activities.HomeScreen
import com.example.subscribble.activities.ShowDetailScreen
import com.example.subscribble.activities.UpcomingBillsScreen
import com.example.subscribble.activities.VideoDonut
import com.example.subscribble.activities.MusicDonut
import com.example.subscribble.activities.TotalLine
@Composable
fun NavGraphControl(navController: NavController){
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
            DataVisualizationScreen(navController = navController)
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
//        composable(route = NavScreen.ShowDetailScreen.route)
//        {
//            val subsId= it.arguments?.getInt("subsId")?: -1
//            ShowDetailScreen(navController = navController, subsId = subsId)
//        }
        composable(
            route = "${NavScreen.ShowDetailScreen.route}/{subsId}",
            arguments = listOf(navArgument("subsId") {type = NavType.IntType})
        ){
            val subsId = it.arguments?.getInt("subsId") ?: -1
            ShowDetailScreen(navController = navController, subsId = subsId)
        }

        composable(
            route = "${NavScreen.EditScreen.route}/{subsId}",
            arguments = listOf(navArgument("subsId") {type = NavType.IntType})
        ){
            val subsId = it.arguments?.getInt("subsId") ?: -1
            EditScreen(navController = navController, subsId = subsId)
        }

        composable(route = NavScreen.MusicDonut.route)
        {
            MusicDonut(navController = navController)
        }

        composable(route = NavScreen.VideoDonut.route)
        {
            VideoDonut(navController = navController)
        }
        composable(route = NavScreen.TotalLine.route)
        {
            val context = LocalContext.current
            TotalLine(context,navController = navController)
        }

    }

}

