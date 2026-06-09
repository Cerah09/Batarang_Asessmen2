package com.josua0056.batarang.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.josua0056.batarang.ui.home.HomeDestination
import com.josua0056.batarang.ui.home.HomeScreen
import com.josua0056.batarang.ui.item.ItemEntryDestination
import com.josua0056.batarang.ui.item.ItemEntryScreen
import com.josua0056.batarang.ui.item.ItemUpdateDestination
import com.josua0056.batarang.ui.item.ItemUpdateScreen

/**
 * Provides Navigation graph for the application.
 */
@Composable
fun BatarangNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToItemEntry = { navController.navigate(ItemEntryDestination.route) },
                navigateToItemUpdate = {
                    navController.navigate("${ItemUpdateDestination.route}/${it}")
                }
            )
        }
        composable(route = ItemEntryDestination.route) {
            ItemEntryScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
        composable(
            route = ItemUpdateDestination.routeWithArgs,
            arguments = listOf(navArgument(ItemUpdateDestination.itemIdArg) {
                type = NavType.IntType
            })
        ) {
            ItemUpdateScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}
