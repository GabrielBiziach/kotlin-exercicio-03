package com.example.exerciciocrudeapi.repository

import com.example.exerciciocrudeapi.database.ContactDao
import com.example.exerciciocrudeapi.model.Contact
import com.example.exerciciocrudeapi.model.ViaCepResponse
import com.example.exerciciocrudeapi.network.ViaCepService

class ContactRepository(
    private val dao: ContactDao,
    private val api: ViaCepService
) {

    val contacts = dao.getAll()

    suspend fun insert(contact: Contact) {
        dao.insertContact(contact)
    }

   suspend fun update(contact: Contact) {
        dao.updateContact(contact)
    }

    suspend fun delete(contact: Contact) {
        dao.deleteContact(contact)
    }

    suspend fun buscarCep(cep: String): ViaCepResponse {
        return api.buscarCep(cep)
    }
}