package com.example.exerciciocrudeapi.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.exerciciocrudeapi.model.Contact
import com.example.exerciciocrudeapi.ui.components.CustomTextField
import com.example.exerciciocrudeapi.viewmodel.ContactViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun ContactFormScreen(
    viewModel: ContactViewModel,
    onSaved: () -> Unit
) {
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

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) { data -> Snackbar(data) } }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            CustomTextField(nome, { nome = it }, "Nome")
            CustomTextField(email, { email = it }, "Email")
            CustomTextField(
                formatTelefone(telefone),
                { telefone = it.filter(Char::isDigit).take(11) },
                "Telefone"
            )
            CustomTextField(
                formatNascimento(nascimento),
                { nascimento = it.filter(Char::isDigit).take(8) },
                "Nascimento"
            )

            CustomTextField(
                formatCep(cep),
                { cep = it.filter(Char::isDigit).take(8) },
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
                    viewModel.saveContact(contact) {
                        scope.launch {
                            snackbarHostState.showSnackbar("Contato salvo com sucesso!")
                            delay(1000)
                            onSaved()
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text("Salvar")
            }
        }
    }
}

private fun formatCep(raw: String): String {
    val digits = raw.filter(Char::isDigit).take(8)
    return when {
        digits.length <= 5 -> digits
        else -> "${digits.substring(0, 5)}-${digits.substring(5)}"
    }
}

private fun formatNascimento(raw: String): String {
    val digits = raw.filter(Char::isDigit).take(8)
    return when {
        digits.length <= 2 -> digits
        digits.length <= 4 -> "${digits.substring(0, 2)}/${digits.substring(2)}"
        else -> "${digits.substring(0, 2)}/${digits.substring(2, 4)}/${digits.substring(4)}"
    }
}

private fun formatTelefone(raw: String): String {
    val digits = raw.filter(Char::isDigit).take(11)
    return when {
        digits.isEmpty() -> ""
        digits.length <= 2 -> "(${digits}"
        digits.length <= 6 -> "(${digits.substring(0, 2)}) ${digits.substring(2)}"
        digits.length <= 10 -> "(${digits.substring(0, 2)}) ${digits.substring(2, 6)}-${digits.substring(6)}"
        else -> "(${digits.substring(0, 2)}) ${digits.substring(2, 7)}-${digits.substring(7)}"
    }
}
