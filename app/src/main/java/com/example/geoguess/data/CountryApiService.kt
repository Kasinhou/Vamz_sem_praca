package com.example.geoguess.data

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

/**
 * Vytvorenie moshi objektu
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

/**
 * Konvertovanie json suboru z url adresy pomocou moshi na kotlin objekty
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(Constants.BASE_URL_ADDRESS)
    .build()

/**
 * Vratenie vysledneho zoznamu krajin
 */
interface CountryApiService {
    @GET(Constants.ADD_URL_ADDRESS)
    suspend fun getProperties():
            List<Country>
}

/**
 * Inicializovanie CountrzApiService na ziskanie konecneho zoznamu krajin
 */
object CountryApi {
    val retrofitService : CountryApiService by lazy {
        retrofit.create(CountryApiService::class.java) }
}