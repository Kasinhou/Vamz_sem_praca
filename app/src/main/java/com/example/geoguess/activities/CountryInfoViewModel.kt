package com.example.geoguess.activities

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.geoguess.data.Country
import com.example.geoguess.data.CountryApi
import kotlinx.coroutines.launch

/**
 * zdielany viewmodel, ktory uchovava informacie o vsetkych krajinach ako aj odpoved ci sa ich podarilo uspesne nacitat
 */
class CountryInfoViewModel : ViewModel() {
    private val _response = MutableLiveData<String>()

    val response: LiveData<String>
        get() = _response

    private val _listOfCountries = MutableLiveData<List<Country>>()

    val listOfCountries: LiveData<List<Country>>
        get() = _listOfCountries


    /**
     * V konstruktore je vypis informacie ze sa viewmodel vytvoril
     */
    init {
        Log.i("COUNTRYINFOVIEWMODEL", "CountryInfoViewModel created!")
    }

    /**
     * Informacia o zaniknuti viewmodelu
     */
    override fun onCleared() {
        super.onCleared()
        Log.i("CountryInfoViewModel", "CountryInfoViewModel destroyed!")
    }

    /**
     * Funkcia asynchroone (pomocou korutin) nacita krajiny ako MutableLiveData
     * Ak neuspesne, priradi do odpovede chybovu hlasku
     */
    fun getCountriesProperties() {
        viewModelScope.launch {
            try {
                _listOfCountries.value = CountryApi.retrofitService.getProperties()
                _response.value = "EVERYTHING IS OK :)"
            } catch (e: Exception) {
                _response.value = "FAILURE: ${e.message} \nIT IS NOT POSSIBLE TO PLAY!"
            }
        }
    }

    /**
     * Vrati krajinu na zaklade zadaneho bezneho nazvu krajiny
     */
    fun getCountry(inputCountry: String = ""): Country? {
        return _listOfCountries.value?.find { it.name.common.lowercase() == inputCountry }
    }

}