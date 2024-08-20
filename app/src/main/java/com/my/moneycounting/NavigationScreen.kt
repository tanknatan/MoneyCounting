package com.my.moneycounting


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavigationScreen(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = Destinations.LoadingScreen.route,
        modifier = Modifier
    ) {
        composable(route = Destinations.LoadingScreen.route) {
            LoadingScreen {
                navHostController.navigate(Destinations.WelcomeScreen.route) {
                    popUpTo(Destinations.LoadingScreen.route) {
                        inclusive = true
                    }
                }
            }
        }
        composable(route = Destinations.WelcomeScreen.route) {
            WelcomeScreen{
                navHostController.navigate(Destinations.FirstStepScreen.route) {
                    popUpTo(Destinations.WelcomeScreen.route) {
                        inclusive = true
                    }
                }
            }


        }
    }
}