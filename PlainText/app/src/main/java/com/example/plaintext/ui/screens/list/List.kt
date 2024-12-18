package com.example.plaintext.ui.screens.list

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.plaintext.R
import com.example.plaintext.ui.screens.login.TopBarComponent
import com.example.plaintext.ui.viewmodel.ListViewModel
import com.example.plaintext.ui.viewmodel.ListViewState
import androidx.compose.foundation.overscroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.plaintext.data.model.PasswordInfo
import com.example.plaintext.ui.screens.JetcasterAppState
import com.example.plaintext.ui.screens.Screen
import com.example.plaintext.ui.screens.hello.Hello_screen
import com.example.plaintext.ui.screens.hello.listViewState
import com.example.plaintext.ui.screens.login.Content
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListView(
    appState: JetcasterAppState
) {
    val context = LocalContext.current
//    Toast.makeText(context, "hello user", Toast.LENGTH_SHORT).show()
//
    val pwd = PasswordInfo(0,"","","","")

    val listOfPasswords = listOf(
        PasswordInfo(1,"Twitter","dev","123","teste"),
        PasswordInfo(1,"Facebook","devTitans","123","teste2"),
        PasswordInfo(1,"Moodle","dev.com","123","teste2"),
    )

    val listViewState = ListViewState(listOfPasswords, true)

    Scaffold( modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = { Text("PlainText") } )
        },
        floatingActionButton = {
            AddButton(
                onClick = {
                    Toast.makeText(context, "Adicionar nova senha", Toast.LENGTH_SHORT).show()
                    val navController = appState.navController
                    navController.navigate(Screen.EditList(pwd))
                }
            )
        }
    )  { padding ->
        ListItemContent(Modifier.padding(padding), listViewState, {
            Toast.makeText(context, "Editar senha - ${it.name}", Toast.LENGTH_SHORT).show()
            val navController = appState.navController
            navController.navigate(Screen.EditList(it))
        })
    }
}

@Composable
fun AddButton(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = { onClick() },
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = MaterialTheme.colorScheme.secondary
    ) {
        Icon(Icons.Filled.Add, "Small floating action button.")
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListItemContent(
    modifier: Modifier,
    listState: ListViewState,
    navigateToEdit: (password: PasswordInfo) -> Unit
) {
    when {
        !listState.isCollected -> {
            LoadingScreen()
        }

        else -> {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
            ) {
                items(listState.passwordList.size) {
                    ListItem(
                        listState.passwordList[it],
                        navigateToEdit
                    )
                }
            }
        }
    }
}

@Composable
fun LoadingScreen() {
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text("Carregando")
    }
}

@Composable
fun ListItem(
    password: PasswordInfo,
    navigateToEdit: (password: PasswordInfo) -> Unit
) {
    val title = password.name
    val subTitle = password.login

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .clickable { navigateToEdit(password) }
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "Logo",
            modifier = Modifier.fillMaxHeight()
        )
        Column(
            modifier = Modifier
                .weight(.7f)
                .padding(horizontal = 5.dp),
        ) {
            Text(title, fontSize = 20.sp)
            Text(subTitle, fontSize = 14.sp)
        }
        Icon(
            Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "Menu",
            tint = Color.White
        )
    }
}