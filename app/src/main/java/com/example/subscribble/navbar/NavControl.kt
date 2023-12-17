@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.subscribble.navbar

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.subscribble.R

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NavControl() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomBar(navController = navController) }
    ) {
        NavGraphControl(navController = navController)
    }

}

@Composable
fun BottomBar(navController: NavController) {
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Add,
        BottomBarScreen.Data_visual,
        BottomBarScreen.Bills
    )

    val navStackBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navStackBackStackEntry?.destination

    val iconSizes = mapOf(
        BottomBarScreen.Data_visual to 32.dp,
        BottomBarScreen.Bills to 32.dp
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(47.dp)
            .background(color = colorResource(id = R.color.default_screen))
            .shadow(
                elevation = 0.9.dp,
                clip = false
            ),
        horizontalArrangement = Arrangement.Center
    ) {
        screens.forEach { screen ->
            if (currentDestination != null) {
                AddItem(
                    screen = screen,
                    currentDestination = currentDestination,
                    navController = navController,
                    iconSizes = iconSizes
                )
            }
        }
    }

}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination,
    navController: NavController,
    iconSizes: Map<BottomBarScreen, Dp>
) {
    val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
    val iconSize = iconSizes[screen] ?: 35.dp

    Row(
        modifier = Modifier
            .weight(1f)
            .background(Color.Transparent)
            .align(Alignment.CenterVertically)
            .clickable{
                navController.navigate(screen.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                }
            },
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = if (selected) screen.icon else screen.icon),
            contentDescription = screen.title,
            tint = if (selected) Color(0xFF0AA6EC) else Color(0xFF808080),
            modifier = Modifier
                .size(iconSize)
                .fillMaxWidth()
                .align(Alignment.CenterVertically)
                .padding(top = 5.dp)
        )
    }

}

