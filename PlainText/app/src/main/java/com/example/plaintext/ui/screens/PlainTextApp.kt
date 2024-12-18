package com.example.plaintext.ui.screens

import android.content.SharedPreferences
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.toRoute
import com.example.plaintext.data.model.PasswordInfo
import com.example.plaintext.ui.screens.editList.EditList
import com.example.plaintext.ui.screens.hello.Hello_screen
import com.example.plaintext.ui.screens.list.AddButton
import com.example.plaintext.ui.screens.list.ListView
import com.example.plaintext.ui.screens.login.Login_screen
import com.example.plaintext.ui.screens.login.TopBarComponent
import com.example.plaintext.ui.screens.preferences.SettingsScreen
import com.example.plaintext.ui.viewmodel.ListViewModel
import com.example.plaintext.ui.viewmodel.PreferencesViewModel
import com.example.plaintext.utils.parcelableType
import kotlin.reflect.typeOf
import com.example.plaintext.ui.screens.Screen.Login
@Composable
fun PlainTextApp(
    appState: JetcasterAppState = rememberJetcasterAppState(),
    preferencesViewModel: PreferencesViewModel = hiltViewModel(),
) {

    NavHost(
        navController = appState.navController,
        startDestination = Screen.Login,
    )
    {
        composable<Screen.Hello>{
            var args = it.toRoute<Screen.Hello>()
            Hello_screen(args)
        }
        composable<Screen.Login>{
            Login_screen(
                appState = appState,
                navigateToSettings = { appState.navController.navigate(Screen.Preferences) },
                navigateToList = {},
                viewModel=preferencesViewModel
            )
        }
        composable<Screen.EditList>(
            typeMap = mapOf(typeOf<PasswordInfo>() to parcelableType<PasswordInfo>())
        ) {
            val args = it.toRoute<Screen.EditList>()
            EditList(
                args,
                navigateBack = {},
                savePassword = { password -> Unit }
            )
        }
        composable<Screen.Preferences>() {
            SettingsScreen(
                navController = appState.navController,
                viewModel=preferencesViewModel,
            )
        }

        composable<Screen.List>{
            ListView(
                appState = appState
            )
        }
    }
}