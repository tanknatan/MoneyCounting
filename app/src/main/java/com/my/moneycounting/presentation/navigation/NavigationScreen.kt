package com.my.moneycounting.presentation.navigation


import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.my.moneycounting.data.Prefs
import com.my.moneycounting.presentation.AddExpensesScreen
import com.my.moneycounting.presentation.AddIncomeScreen
import com.my.moneycounting.presentation.CalculatorScreen
import com.my.moneycounting.presentation.FirstStepScreen
import com.my.moneycounting.presentation.LoadingScreen
import com.my.moneycounting.presentation.MainScreen
import com.my.moneycounting.presentation.NewsScreen
import com.my.moneycounting.presentation.NewsViewModel
import com.my.moneycounting.presentation.SecondStepScreen
import com.my.moneycounting.presentation.SettingsScreen
import com.my.moneycounting.presentation.StartStepScreen
import com.my.moneycounting.presentation.WebViewScreen
import com.my.moneycounting.presentation.WelcomeScreen


@Composable
fun NavigationScreen(
    navHostController: NavHostController,
) {
    val newsViewModel: NewsViewModel = viewModel()
    NavHost(
        navController = navHostController,
        startDestination = Destinations.LoadingScreen.route,
        modifier = Modifier,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
    ) {
        composable(route = Destinations.LoadingScreen.route) {
            LoadingScreen {
                if (Prefs.startStepCompleted) {
                    runCatching {
                        navHostController.navigate(Destinations.MainScreen.route) {
                            popUpTo(Destinations.LoadingScreen.route) {
                                inclusive = true
                            }
                        }
                    }
                } else {
                    runCatching {
                        navHostController.navigate(Destinations.WelcomeScreen.route) {
                            popUpTo(Destinations.LoadingScreen.route) {
                                inclusive = true
                            }
                        }
                    }
                }

            }
        }
        composable(route = Destinations.WelcomeScreen.route) {
            WelcomeScreen {
                runCatching {
                    navHostController.navigate(Destinations.FirstStepScreen.route) {
                        popUpTo(Destinations.WelcomeScreen.route) {
                            inclusive = true
                        }
                    }
                }
            }


        }
        composable(route = Destinations.FirstStepScreen.route) {
            FirstStepScreen {
                runCatching {
                    navHostController.navigate(Destinations.SecondStepScreen.route) {
                        popUpTo(Destinations.FirstStepScreen.route) {
                            inclusive = true
                        }
                    }
                }
            }
        }
        composable(route = Destinations.SecondStepScreen.route) {
            SecondStepScreen {
                runCatching {
                    navHostController.navigate(Destinations.StartStepScreen.route) {
                        popUpTo(Destinations.SecondStepScreen.route) {
                            inclusive = true
                        }
                    }
                }
            }
        }
        composable(route = Destinations.StartStepScreen.route) {
            StartStepScreen {
                runCatching {
                    navHostController.navigate(Destinations.MainScreen.route) {
                        popUpTo(Destinations.StartStepScreen.route) {
                            inclusive = true
                        }
                    }
                }
            }
        }
        composable(route = Destinations.MainScreen.route) {
            MainScreen(
                onAddTransaction = { type ->
                    runCatching {
                        navHostController.navigate("${Destinations.AddTransactionScreen.route}/$type")
                    }
                },
                onSettingsClick = {
                    runCatching {
                        navHostController.navigate(Destinations.SettingsScreen.route) {
                            launchSingleTop = true
                            popUpTo(Destinations.MainScreen.route)
                        }
                    }
                },
                onNotificationClick = {
                    runCatching {
                        navHostController.navigate(Destinations.NewsScreen.route) {
                            launchSingleTop = true
                            popUpTo(Destinations.MainScreen.route)
                        }
                    }
                },
                onBankClick = {
                    runCatching {
                        navHostController.navigate(Destinations.CalculatorScreen.route) {
                            launchSingleTop = true
                            popUpTo(Destinations.MainScreen.route)
                        }
                    }
                }
            )
        }
        composable(route = Destinations.SettingsScreen.route) {
            SettingsScreen(
                onBackClick = navHostController::popBackStack,
                onNotificationClick = {
                    runCatching {
                        navHostController.navigate(Destinations.NewsScreen.route) {
                            launchSingleTop = true
                            popUpTo(Destinations.SettingsScreen.route)
                        }
                    }
                },
                onReportClick = {
                    runCatching {
                        navHostController.navigate(Destinations.MainScreen.route) {
                            launchSingleTop = true
                            popUpTo(Destinations.SettingsScreen.route)
                        }
                    }
                },
                onBankClick = {
                    runCatching {
                        navHostController.navigate(Destinations.CalculatorScreen.route) {
                            launchSingleTop = true
                            popUpTo(Destinations.SettingsScreen.route)
                        }
                    }

                },
                onTermsClick = {
                    runCatching {
                        navHostController.navigate(Destinations.TermsAndConditionsScreen.route) {
                            launchSingleTop = true
                            popUpTo(Destinations.SettingsScreen.route)
                        }
                    }
                },
                onPrivacyClick = {
                    runCatching {
                        navHostController.navigate(Destinations.PrivacyPolicyScreen.route) {
                            launchSingleTop = true
                            popUpTo(Destinations.SettingsScreen.route)
                        }
                    }
                }

            )
        }
        composable(route = Destinations.CalculatorScreen.route) {
            CalculatorScreen(
                onBackClick = {
                    runCatching {
                        navHostController.popBackStack()
                    }
                },
                onSettingsClick = {
                    runCatching {
                        navHostController.navigate(Destinations.SettingsScreen.route) {
                            launchSingleTop = true
                            popUpTo(Destinations.CalculatorScreen.route)
                        }
                    }
                },
                onNotificationClick = {
                    runCatching {
                        navHostController.navigate(Destinations.NewsScreen.route) {
                            launchSingleTop = true
                            popUpTo(Destinations.CalculatorScreen.route)
                        }
                    }
                },
                onReportClick = {
                    runCatching {
                        navHostController.navigate(Destinations.MainScreen.route) {
                            launchSingleTop = true
                            popUpTo(Destinations.CalculatorScreen.route)
                        }
                    }
                },
                onCalculateClick = { amount, term, rate, isAnnuity ->
                    // Perform the loan calculation here and handle the result

                }
            )
        }

        composable(route = Destinations.NewsScreen.route) {
            NewsScreen(
                viewModel = newsViewModel,
                onSettingsClick = {
                    runCatching {
                        navHostController.navigate(Destinations.SettingsScreen.route) {
                            launchSingleTop = true
                            popUpTo(Destinations.MainScreen.route)
                        }
                    }
                },
                onReportClick = {
                    runCatching {
                        navHostController.navigate(Destinations.MainScreen.route) {
                            launchSingleTop = true
                            popUpTo(Destinations.MainScreen.route)
                        }
                    }
                },
                onBankClick = {
                    runCatching {
                        navHostController.navigate(Destinations.CalculatorScreen.route) {
                            launchSingleTop = true
                            popUpTo(Destinations.MainScreen.route)
                        }
                    }
                },
                onBackClick = navHostController::popBackStack,
            )
        }
        composable(route = Destinations.TermsAndConditionsScreen.route) {
            WebViewScreen(url = "https://www.google.com/")
        }
        composable(route = Destinations.PrivacyPolicyScreen.route) {
            WebViewScreen(url = "https://www.google.com/")
        }
        composable(route = "${Destinations.AddTransactionScreen.route}/{type}") {
            val type = it.arguments?.getString("type")
            if (type == "Expenses") {
                AddExpensesScreen(onBackClick = navHostController::popBackStack)
            }
            else {
                AddIncomeScreen(onBackClick = navHostController::popBackStack)
            }
        }
    }
}