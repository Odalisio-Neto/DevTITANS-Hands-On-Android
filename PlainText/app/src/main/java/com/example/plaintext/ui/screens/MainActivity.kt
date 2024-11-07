package com.example.plaintext.ui.screens

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.plaintext.ui.screens.preferences.SettingsScreen
import com.example.plaintext.ui.theme.PlainTextTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val preferences: SharedPreferences = getPreferences(MODE_PRIVATE)
        enableEdgeToEdge()
        setContent {
            PlainTextTheme {
                PlainTextApp(
                    preferences=preferences
                )
            }
        }
    }
}

@Serializable
object ScreenLogin

@Serializable
object ScreenPreferences