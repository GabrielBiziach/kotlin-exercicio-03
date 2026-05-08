package com.example.exerciciocrudeapi.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.exerciciocrudeapi.database.AppDatabase
import com.example.exerciciocrudeapi.model.Contact
import com.example.exerciciocrudeapi.model.ViaCepResponse
import com.example.exerciciocrudeapi.remote.RetrofitClient
import com.example.exerciciocrudeapi.repository.ContactRepository
import java.net.UnknownHostException
import java.net.SocketTimeoutException
import kotlinx.coroutines.launch
import retrofit2.HttpException

class ContactViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        private const val TAG = "ContactViewModel"
    }

    private val repository: ContactRepository

    init {
        val dao = AppDatabase.getDatabase(application).contactDao()
        val api = RetrofitClient.api
        repository = ContactRepository(dao, api)
    }

    val contacts = repository.contacts.asLiveData()

    fun saveContact(contact: Contact, onSaved: (() -> Unit)? = null) {
        viewModelScope.launch {
            repository.insert(contact)
            onSaved?.invoke()
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
            } catch (e: UnknownHostException) {
                Log.e(TAG, "DNS/connectivity error while searching CEP: $cep", e)
                onResult(null)
            } catch (e: SocketTimeoutException) {
                Log.e(TAG, "Timeout while searching CEP: $cep", e)
                onResult(null)
            } catch (e: HttpException) {
                Log.e(TAG, "HTTP error ${e.code()} while searching CEP: $cep", e)
                onResult(null)
            } catch (e: Exception) {
                Log.e(TAG, "Unexpected error while searching CEP: $cep", e)
                onResult(null)
            }
        }
    }

}