package com.my.moneycounting

sealed class Destinations(val route: String) {

    data object LoadingScreen : Destinations("splash_screen")

    data object WelcomeScreen : Destinations("welcome_screen")
    data object FirstStepScreen : Destinations("first_stap")

}