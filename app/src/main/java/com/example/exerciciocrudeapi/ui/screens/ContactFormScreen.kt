package com.example.exerciciocrudeapi.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.exerciciocrudeapi.model.Contact
import com.example.exerciciocrudeapi.ui.components.CustomTextField
import com.example.exerciciocrudeapi.viewmodel.ContactViewModel


@Composable
fun ContactFormScreen(viewModel: ContactViewModel) {

    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }
    var nascimento by remember { mutableStateOf("") }
    var cep by remember { mutableStateOf("") }
    var bairro by remember { mutableStateOf("") }
    var logradouro by remember { mutableStateOf("") }
    var numero by remember { mutableStateOf("") }
    var estado by remember { mutableStateOf("") }
    var cidade by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {

        CustomTextField(nome, { nome = it }, "Nome")
        CustomTextField(email, { email = it }, "Email")
        CustomTextField(
            telefone,
            { telefone = it.filter(Char::isDigit) },
            "Telefone"
        )
        CustomTextField(
            nascimento,
            { nascimento = it.filter(Char::isDigit) },
            "Nascimento"
        )

        CustomTextField(
            cep,
            { cep = it.filter(Char::isDigit) },
            "CEP"
        )

        Button(
            onClick = {
                if (cep.length == 8) {
                    viewModel.searchCep(cep) { result ->
                        result?.let {
                            bairro = it.bairro
                            logradouro = it.logradouro
                            cidade = it.localidade
                            estado = it.uf
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text("Buscar CEP")
        }

        CustomTextField(bairro, { bairro = it }, "Bairro", readOnly = true)
        CustomTextField(logradouro, { logradouro = it }, "Logradouro", readOnly = true)
        CustomTextField(numero, { numero = it }, "Número")
        CustomTextField(estado, { estado = it }, "Estado", readOnly = true)
        CustomTextField(cidade, { cidade = it }, "Cidade", readOnly = true)

        Button(
            onClick = {
                val contact = Contact(
                    nome = nome,
                    email = email,
                    telefone = telefone,
                    nascimento = nascimento,
                    cep = cep,
                    bairro = bairro,
                    logradouro = logradouro,
                    numero = numero,
                    estado = estado,
                    cidade = cidade
                )

                viewModel.saveContact(contact)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Salvar")
        }
    }
}
