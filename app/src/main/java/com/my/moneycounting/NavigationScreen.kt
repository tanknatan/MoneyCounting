package com.my.moneycounting


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import android.content.Context
import android.content.SharedPreferences
import androidx.compose.ui.platform.LocalContext

fun isStartStepCompleted(context: Context): Boolean {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    return sharedPreferences.getBoolean("StartStepCompleted", false)
}

@Composable
fun NavigationScreen(
    navHostController: NavHostController
) {
    val startStepCompleted = isStartStepCompleted(context = LocalContext.current)
    NavHost(
        navController = navHostController,
        startDestination = Destinations.LoadingScreen.route,
        modifier = Modifier
    ) {
        composable(route = Destinations.LoadingScreen.route) {
            LoadingScreen {


                if (startStepCompleted) {
                    navHostController.navigate(Destinations.MainScreen.route) {
                        popUpTo(Destinations.LoadingScreen.route) {
                            inclusive = true
                        }
                    }
                } else {
                    navHostController.navigate(Destinations.WelcomeScreen.route) {
                        popUpTo(Destinations.LoadingScreen.route) {
                            inclusive = true
                        }
                    }
                }

            }
        }
        composable(route = Destinations.WelcomeScreen.route) {
            WelcomeScreen {
                navHostController.navigate(Destinations.FirstStepScreen.route) {
                    popUpTo(Destinations.WelcomeScreen.route) {
                        inclusive = true
                    }
                }
            }


        }
        composable(route = Destinations.FirstStepScreen.route) {
            FirstStepScreen{
                navHostController.navigate(Destinations.SecondStepScreen.route) {
                    popUpTo(Destinations.FirstStepScreen.route) {
                        inclusive = true
                    }
                }
            }
        }
        composable(route = Destinations.SecondStepScreen.route) {
            SecondStepScreen{
                navHostController.navigate(Destinations.StartStepScreen.route) {
                    popUpTo(Destinations.SecondStepScreen.route) {
                        inclusive = true
                    }
                }
            }
        }
        composable(route = Destinations.StartStepScreen.route) {
            StartStepScreen{

                navHostController.navigate(Destinations.MainScreen.route) {
                    popUpTo(Destinations.StartStepScreen.route) {
                        inclusive = true
                    }
                }
            }
        }
    }
}