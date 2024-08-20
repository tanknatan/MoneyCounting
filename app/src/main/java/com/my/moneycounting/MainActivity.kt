package com.my.moneycounting

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.my.moneycounting.ui.theme.MoneyCountingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navHostController = rememberNavController()
            MoneyCountingTheme {
                NavigationScreen(navHostController)
            }
        }
    }

}

