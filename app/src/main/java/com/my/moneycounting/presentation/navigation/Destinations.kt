package com.my.moneycounting.presentation.navigation

sealed class Destinations(val route: String) {

    data object LoadingScreen : Destinations("splash_screen")
    data object WelcomeScreen : Destinations("welcome_screen")
    data object FirstStepScreen : Destinations("first_stap")
    data object SecondStepScreen : Destinations("second_step")
    data object StartStepScreen : Destinations("start_step")
    data object MainScreen : Destinations("main_screen")
    data object SettingsScreen : Destinations("settings_screen")
    data object CalculatorScreen : Destinations("loan_calculator_screen")

    data object AddTransactionScreen : Destinations("add_transaction_screen")
    data object EditTransactionScreen : Destinations("edit_transaction_screen")
    data object AddCategoryScreen : Destinations("add_category_screen")

}