package com.example.plaintext.ui.screens.login

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.plaintext.R
import com.example.plaintext.ui.screens.JetcasterAppState
import com.example.plaintext.ui.screens.Screen
import com.example.plaintext.ui.viewmodel.PreferencesViewModel
data class LoginState(
    val preencher: Boolean,
    val login: String,
    val navigateToSettings: () -> Unit,
    val navigateToList: (name: String) -> Unit,
    val checkCredentials: (login: String, password: String) -> Boolean,
)


@Composable
fun Login_screen(
    appState: JetcasterAppState,
    navigateToSettings: () -> Unit,
    navigateToList: () -> Unit,
    viewModel: PreferencesViewModel = hiltViewModel()
) {
    Box(modifier = Modifier.fillMaxSize()){
        Scaffold( modifier = Modifier.fillMaxSize(), topBar = {
            TopBarComponent(
                navigateToSettings = navigateToSettings
            )
        })  { innerPadding ->
            Content(modifier = Modifier.padding(innerPadding), navController = appState.navController) // acessa o navController do appState
        }
    }
}

@Composable
fun MyAlertDialog(shouldShowDialog: MutableState<Boolean>) {
    if (shouldShowDialog.value) {
        AlertDialog(
            onDismissRequest = {
                shouldShowDialog.value = false
            },

            title = { Text(text = "Sobre") },
            text = { Text(text = "PlainText Password Manager v1.0") },
            confirmButton = {
                Button(
                    onClick = { shouldShowDialog.value = false }
                ) {
                    Text(text = "Ok")
                }
            }
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopBarComponent(
    navigateToSettings: (() -> Unit?)? = null,
    //navigateToSensores: (() -> Unit?)? = null,
) {
    var expanded by remember { mutableStateOf(false) }
    val shouldShowDialog = remember { mutableStateOf(false) }

    if (shouldShowDialog.value) {
        MyAlertDialog(shouldShowDialog = shouldShowDialog)
    }

    TopAppBar(
        title = { Text("PlainText") },
        actions = {
            if (navigateToSettings != null){ //&& navigateToSensores != null) {
                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "Menu")
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Configurações") },
                        onClick = {
                            navigateToSettings();
                            expanded = false;
                        },
                        modifier = Modifier.padding(8.dp)
                    )
                    DropdownMenuItem(
                        text = {
                            Text("Sobre");
                        },
                        onClick = {
                            shouldShowDialog.value = true;
                            expanded = false;
                        },
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    )
}

@Composable
fun Content(modifier: Modifier, navController: NavHostController) {
    var login by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var checked by remember { mutableStateOf(false) }

    var context = LocalContext.current

    Column(modifier) {
        Row ( modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF57cc99))
            .padding(10.dp, 20.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically)
        {
            Image (
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "Logo"
            )
            Spacer(Modifier.padding(10.dp))
            Text("\"The most secure password manager\" Bob and Alice")
        }
        Spacer(Modifier.padding(10.dp))
        Column(
            modifier = modifier
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text("Digite suas credenciais para continuar", fontSize = 17.sp)

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Login:", modifier = Modifier.width(60.dp))
                OutlinedTextField (
                    value = login,
                    onValueChange = { login = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                        .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.small)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )

            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Senha:", modifier = Modifier.width(60.dp))
                OutlinedTextField(
                    value = senha,
                    onValueChange = { senha = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                        .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.small)
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    visualTransformation = PasswordVisualTransformation()
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = checked,
                    onCheckedChange = { checked = it }
                )
                Text("Salvar as informações de login")
            }

            Button(
                onClick = {
                    val name = login;
                    if (name == "") {
                        Toast.makeText(context, "Digite seu login", Toast.LENGTH_SHORT).show()
                    } else {
                        navController.navigate(Screen.List)
                    }

                },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF378CC8)),
                enabled = true

            ) { Text("Enviar") }



        }
    }
}
