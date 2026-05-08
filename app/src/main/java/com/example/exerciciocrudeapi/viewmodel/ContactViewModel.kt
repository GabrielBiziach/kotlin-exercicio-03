package com.example.exerciciocrudeapi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.exerciciocrudeapi.model.Contact
import com.example.exerciciocrudeapi.model.ViaCepResponse
import com.example.exerciciocrudeapi.repository.ContactRepository
import kotlinx.coroutines.launch

class ContactViewModel(private val repository: ContactRepository) : ViewModel() {

    val contacts = repository.contacts.asLiveData()

    fun saveContact(contact: Contact) {
        viewModelScope.launch {
            repository.insert(contact)
        }
    }

    fun delete(contact: Contact) {
        viewModelScope.launch {
            repository.delete(contact)
        }
    }


    fun searchCep(cep: String, onResult: (ViaCepResponse?) -> Unit) {
        viewModelScope.launch {
            try {
                val result = repository.buscarCep(cep)
                onResult(result)
            } catch (e: Exception) {
                e.printStackTrace()
                onResult(null)
            }
        }
    }

}