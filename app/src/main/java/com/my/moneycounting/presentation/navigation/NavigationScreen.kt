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
            FirstStepScreen {
                navHostController.navigate(Destinations.SecondStepScreen.route) {
                    popUpTo(Destinations.FirstStepScreen.route) {
                        inclusive = true
                    }
                }
            }
        }
        composable(route = Destinations.SecondStepScreen.route) {
            SecondStepScreen {
                navHostController.navigate(Destinations.StartStepScreen.route) {
                    popUpTo(Destinations.SecondStepScreen.route) {
                        inclusive = true
                    }
                }
            }
        }
        composable(route = Destinations.StartStepScreen.route) {
            StartStepScreen {
                navHostController.navigate(Destinations.MainScreen.route) {
                    popUpTo(Destinations.StartStepScreen.route) {
                        inclusive = true
                    }
                }
            }
        }
        composable(route = Destinations.MainScreen.route) {
            MainScreen(
                onBackClick = navHostController::navigateUp,
                onSettingsClick = {
                    navHostController.navigate(Destinations.SettingsScreen.route) {
                        launchSingleTop = true
                        popUpTo(Destinations.MainScreen.route)
                    }
                },
                onNotificationClick = {
                    navHostController.navigate(Destinations.NewsScreen.route) {
                        launchSingleTop = true
                        popUpTo(Destinations.MainScreen.route)
                    }
                },
                onBankClick = {
                    navHostController.navigate(Destinations.CalculatorScreen.route) {
                        launchSingleTop = true
                        popUpTo(Destinations.MainScreen.route)
                    }
                },
                onAddTransaction = { type ->
                    navHostController.navigate("${Destinations.AddTransactionScreen.route}/$type")
                }
                )
        }
        composable(route = Destinations.SettingsScreen.route) {
            SettingsScreen(
                onBackClick = navHostController::navigateUp,
                onNotificationClick = {
                    navHostController.navigate(Destinations.NewsScreen.route) {
                        launchSingleTop = true
                        popUpTo(Destinations.SettingsScreen.route)
                    }
                },
                onReportClick = {
                    navHostController.navigate(Destinations.MainScreen.route) {
                        launchSingleTop = true
                        popUpTo(Destinations.SettingsScreen.route)
                    }
                },
                onBankClick = {
                    navHostController.navigate(Destinations.CalculatorScreen.route) {
                        launchSingleTop = true
                        popUpTo(Destinations.SettingsScreen.route)
                    }

                },
                onTermsClick = {
                    navHostController.navigate(Destinations.TermsAndConditionsScreen.route) {
                        launchSingleTop = true
                        popUpTo(Destinations.SettingsScreen.route)
                    }
                },
                onPrivacyClick = {
                    navHostController.navigate(Destinations.PrivacyPolicyScreen.route) {
                        launchSingleTop = true
                        popUpTo(Destinations.SettingsScreen.route)
                    }
                }

            )
        }
        composable(route = Destinations.CalculatorScreen.route) {
            CalculatorScreen(
                onBackClick = {
                    navHostController.navigateUp()
                },
                onSettingsClick = {
                    navHostController.navigate(Destinations.SettingsScreen.route) {
                        launchSingleTop = true
                        popUpTo(Destinations.CalculatorScreen.route)
                    }
                },
                onNotificationClick = {
                    navHostController.navigate(Destinations.NewsScreen.route) {
                        launchSingleTop = true
                        popUpTo(Destinations.CalculatorScreen.route)
                    }
                },
                onReportClick = {
                    navHostController.navigate(Destinations.MainScreen.route) {
                        launchSingleTop = true
                        popUpTo(Destinations.CalculatorScreen.route)
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
                    navHostController.navigate(Destinations.SettingsScreen.route) {
                        launchSingleTop = true
                        popUpTo(Destinations.MainScreen.route)
                    }
                },
                onReportClick = {
                    navHostController.navigate(Destinations.MainScreen.route) {
                        launchSingleTop = true
                        popUpTo(Destinations.MainScreen.route)
                    }
                },
                onBankClick = {
                    navHostController.navigate(Destinations.CalculatorScreen.route) {
                        launchSingleTop = true
                        popUpTo(Destinations.MainScreen.route)
                    }
                },
                onBackClick = navHostController::navigateUp,
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
                AddExpensesScreen(onBackClick = navHostController::navigateUp)
            }
            else {
                AddIncomeScreen(onBackClick = navHostController::navigateUp)
            }
        }
    }
}