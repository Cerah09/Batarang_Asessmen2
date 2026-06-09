package com.josua0056.batarang

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.navigation.compose.rememberNavController
import com.josua0056.batarang.ui.navigation.BatarangNavHost
import com.josua0056.batarang.ui.theme.BatarangTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BatarangTheme {
                val navController = rememberNavController()
                BatarangNavHost(
                    navController = navController,
                    modifier = androidx.compose.ui.Modifier.fillMaxSize()
                )
            }
        }
    }
}
