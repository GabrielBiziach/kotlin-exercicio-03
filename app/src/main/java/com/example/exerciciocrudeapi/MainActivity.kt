package com.example.exerciciocrudeapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.example.exerciciocrudeapi.database.AppDatabase
import com.example.exerciciocrudeapi.remote.RetrofitClient
import com.example.exerciciocrudeapi.repository.ContactRepository
import com.example.exerciciocrudeapi.ui.screens.ContactFormScreen
import com.example.exerciciocrudeapi.ui.screens.ContactListScreen
import com.example.exerciciocrudeapi.ui.theme.ExercicioCrudEAPITheme
import com.example.exerciciocrudeapi.viewmodel.ContactViewModel
import com.example.exerciciocrudeapi.viewmodel.ContactViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val db = AppDatabase.getDatabase(this)
        val dao = db.contactDao()
        val api = RetrofitClient.api

        val repository = ContactRepository(dao, api)

        val viewModelFactory = ContactViewModelFactory(repository)
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
        ContactFormScreen(viewModel)
    } else {
        Column {
            Button(onClick = { showForm = true }) {
                Text("Novo Contato")
            }

            ContactListScreen(viewModel)
        }
    }
}
