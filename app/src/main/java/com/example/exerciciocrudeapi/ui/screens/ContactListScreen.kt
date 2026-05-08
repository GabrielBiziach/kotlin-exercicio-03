package com.example.exerciciocrudeapi.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.exerciciocrudeapi.viewmodel.ContactViewModel
import kotlin.collections.emptyList

@Composable
fun ContactListScreen(viewModel: ContactViewModel) {
    val contacts by viewModel.contacts.observeAsState(emptyList())

    LazyColumn {
        items(contacts) { contact ->

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {

                    Text("Nome: ${contact.nome}")
                    Text("Email: ${contact.email}")
                    Text("Telefone: ${contact.telefone}")

                    Row {
                        Button(onClick = {
                            viewModel.delete(contact)
                        }) {
                            Text("Excluir")
                        }
                    }
                }
            }
        }
    }



}