package com.example.exerciciocrudeapi.network

import com.example.exerciciocrudeapi.model.ViaCepResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ViaCepService {

    @GET("ws/{cep}/json/")
    suspend fun buscarCep(@Path("cep") cep: String): ViaCepResponse
}