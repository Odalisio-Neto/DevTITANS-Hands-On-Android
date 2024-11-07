package com.example.plaintext.ui.screens.preferences


import android.content.SharedPreferences
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.plaintext.ui.screens.login.TopBarComponent
import com.example.plaintext.ui.screens.util.PreferenceInput
import com.example.plaintext.ui.screens.util.PreferenceItem
import com.example.plaintext.ui.viewmodel.PreferencesState
import com.example.plaintext.ui.viewmodel.PreferencesViewModel
import kotlinx.coroutines.delay

@Composable
fun SettingsScreen(
    navController: NavHostController?,
    preferences: SharedPreferences?,
    viewModel: PreferencesViewModel = hiltViewModel(),
){
    var initializedFromPreferences by remember {
        mutableStateOf(false)
    }
    if(!initializedFromPreferences){
        val state = loadFromPreferences(preferences!!)
        viewModel.updateLogin(state.login)
        viewModel.updatePassword(state.password)
        viewModel.updatePreencher(state.preencher)
        initializedFromPreferences = true
    }

    Scaffold(
        topBar = {
            TopBarComponent()
        }
    ){ padding ->
        SettingsContent(modifier = Modifier.padding(padding), viewModel, preferences)
    }
}

@Composable
fun SettingsContent(modifier: Modifier = Modifier, viewModel: PreferencesViewModel, preferences: SharedPreferences?) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())){

        PreferenceInput(
            title = "Preencher Login",
            label = "Login",
            fieldValue = viewModel.preferencesState.login,
            summary = "Preencher login na tela inicial"
        ){
            viewModel.updateLogin(it)
            saveToPreferences(preferences!!, viewModel.preferencesState)
        }

        PreferenceInput(
            title = "Setar Senha",
            label = "Label",
            fieldValue = viewModel.preferencesState.password,
            summary = "Senha para entrar no sistema"
        ){
            viewModel.updatePassword(it)
            saveToPreferences(preferences!!, viewModel.preferencesState)
        }

        PreferenceItem(
            title = "Preencher Login",
            summary = "Preencher login na tela inicial",
            onClick = {
                viewModel.updatePreencher(!viewModel.preferencesState.preencher)
                saveToPreferences(preferences!!, viewModel.preferencesState)
            },
            control = {
                Switch(
                    checked = viewModel.preferencesState.preencher, // deve ler o estado que representa se o switch está ligado ou não
                    onCheckedChange = {
                        // deve alterar o estado que representa se o switch está ligado ou não
                        viewModel.updatePreencher(!viewModel.preferencesState.preencher)
                    }
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    SettingsScreen(null, null )
}

fun loadFromPreferences(preferences: SharedPreferences) :PreferencesState {
    return PreferencesState(
        preferences.getString("login", "devtitans")!!,
        preferences.getString("password", "123")!!,
        preferences.getBoolean("preencher", true),
    )
}

fun saveToPreferences(preferences: SharedPreferences, state: PreferencesState) : Unit {
    preferences.edit {
        putString("login", state.login)
        putString("password", state.password)
        putBoolean("preencher", state.preencher)
    }
}