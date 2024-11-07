package com.example.plaintext.ui.screens.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

// data class para representar o estado da tela de login
data class LoginViewState(
    val login: String = "",
    val senha: String = "",
    val lembrarLogin: Boolean = false
)

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {
    var loginViewState by mutableStateOf(LoginViewState())
        private set

    // atualiza campo de login
    fun onLoginChanged(newLogin: String) {
        loginViewState = loginViewState.copy(login = newLogin)
    }

    // atualiza o campo de senha
    fun onSenhaChanged(newSenha: String) {
        loginViewState = loginViewState.copy(senha = newSenha)
    }

    // atualiza o estado do checkbox "Lembrar Login"
    fun onLembrarLoginChanged(shouldRemember: Boolean) {
        loginViewState = loginViewState.copy(lembrarLogin = shouldRemember)
    }

    // exemplo de função para simular uma ação de login
    fun realizarLogin(onLoginSuccess: () -> Unit, onLoginError: () -> Unit) {
        viewModelScope.launch {
            if (loginViewState.login.isNotEmpty() && loginViewState.senha.isNotEmpty()) {
                // Simula um login bem-sucedido
                onLoginSuccess()
            } else {
                // Login falhou
                onLoginError()
            }
        }
    }


}
