package com.example.geoguess.data

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL_ADDRESS = "https://restcountries.com/v3.1/"
//private const val URL_ADDRESS = "https://android-kotlin-fun-mars-server.appspot.com/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create())
    .baseUrl(BASE_URL_ADDRESS)
    .build()

interface CountryApiService {
    @GET("all?fields=name,currencies,idd,capital,languages,latlng,borders,area,maps,population,fifa,timezones,continents,flags,coatOfArms")
    fun getProperties():
            Call<List<Country>>
}

object CountryApi {
    val retrofitService : CountryApiService by lazy {
        retrofit.create(CountryApiService::class.java) }
}