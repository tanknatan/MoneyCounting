package com.my.moneycounting

import android.os.Build
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Destinations(val route: String) {

    data object LoadingScreen : Destinations("splash_screen")

    data object WelcomeScreen : Destinations("welcome_screen")
    data object FirstStepScreen : Destinations("first_stap")

}