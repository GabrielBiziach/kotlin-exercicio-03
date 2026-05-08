package com.example.exerciciocrudeapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.example.exerciciocrudeapi.ui.screens.ContactFormScreen
import com.example.exerciciocrudeapi.ui.screens.ContactListScreen
import com.example.exerciciocrudeapi.ui.theme.ExercicioCrudEAPITheme
import com.example.exerciciocrudeapi.viewmodel.ContactViewModel
import com.example.exerciciocrudeapi.viewmodel.ContactViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModelFactory = ContactViewModelFactory(application)
        val viewModel = ViewModelProvider(this, viewModelFactory)[ContactViewModel::class.java]

        setContent {
            ExercicioCrudEAPITheme {
                MainScreen(viewModel)
            }
        }
    }
}


@Composable
fun MainScreen(viewModel: ContactViewModel) {

    var showForm by remember { mutableStateOf(false) }

    if (showForm) {
        ContactFormScreen(
            viewModel = viewModel,
            onSaved = { showForm = false }
        )
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = { showForm = true }) {
                Text("Novo Contato")
            }

            Column(modifier = Modifier.fillMaxWidth()) {
                ContactListScreen(viewModel)
            }
        }
    }
}
