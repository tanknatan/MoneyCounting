package com.my.moneycounting.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.my.moneycounting.data.Prefs
import com.my.moneycounting.presentation.navigation.NavigationScreen
import com.my.moneycounting.ui.theme.MoneyCountingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Prefs.init(applicationContext)
        setContent {
            val navHostController = rememberNavController()
            MoneyCountingTheme {
                NavigationScreen(navHostController)
            }
        }
    }

}

