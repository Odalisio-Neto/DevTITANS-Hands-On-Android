package com.example.plaintext.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import javax.inject.Singleton
import android.content.SharedPreferences
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.edit
import com.example.plaintext.ui.screens.PreferencesHandler

data class PreferencesState(
    var login: String,
    var password: String,
    var preencher: Boolean
)

@HiltViewModel()
class PreferencesViewModel @Inject constructor(
    handle: SavedStateHandle,
) : ViewModel() {
    val preferences = PreferencesHandler.preferences!!
    var preferencesState by mutableStateOf(loadFromPreferences(preferences))
        private set

    fun updateLogin(login: String) {
        preferencesState = preferencesState.copy(login=login)
        preferences.edit {
            putString("login", login)
        }
    }

    fun updatePassword(password: String) {
        preferencesState = preferencesState.copy(password=password)
        preferences.edit {
            putString("password", password)
        }
    }

    fun updatePreencher(preencher: Boolean) {
        preferencesState = preferencesState.copy(preencher=preencher)
        preferences.edit {
            putBoolean("preencher", preencher)
        }
    }

    fun checkCredentials(login: String, password: String): Boolean{
        return login == preferencesState.login && password == preferencesState.password
    }
}

fun loadFromPreferences(preferences: SharedPreferences) :PreferencesState {
    return PreferencesState(
        preferences.getString("login", "devtitans")!!,
        preferences.getString("password", "123")!!,
        preferences.getBoolean("preencher", true),
    )
}