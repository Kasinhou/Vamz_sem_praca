package com.example.geoguess.activities

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.geoguess.data.Country
import com.example.geoguess.data.CountryApi
import kotlinx.coroutines.launch

class CountryInfoViewModel : ViewModel() {
    // The internal MutableLiveData String that stores the most recent response
    private val _response = MutableLiveData<String>()

    // The external immutable LiveData for the response String
    val response: LiveData<String>
        get() = _response

    private val _listOfCountries = MutableLiveData<List<Country>>()

    val listOfCountries: LiveData<List<Country>>
        get() = _listOfCountries

    /*private var _countries : List<Country> = listOf()

    val countries : List<Country>
        get() = _countries*/


    /**
     * Call getCountriesProperties() on init so we can display status immediately.
     */
    init {
        //getCountriesProperties()
        Log.i("COUNTRYINFOVIEWMODEL", "CountryInfoViewModel created!")
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("CountryInfoViewModel", "CountryInfoViewModel destroyed!")
    }

    fun getCountriesProperties() {
        viewModelScope.launch {
            try {
                _listOfCountries.value = CountryApi.retrofitService.getProperties()
                //_countries = CountryApi.retrofitService.getProperties()
                _response.value = "EVERYTHING IS OK :)"
            } catch (e: Exception) {
                _response.value = "FAILURE: ${e.message} \nIT IS NOT POSSIBLE TO PLAY!"
            }
        }
    }

    fun getCountry(inputCountry: String = ""): Country? {
        /* val country = _listOfCountries.value?.find { it.name.official == inputCountry }
        return if (country == null) {
            _listOfCountries.value?.get(0)
        } else {
            country
        }*/
        return _listOfCountries.value?.find { it.name.common.lowercase() == inputCountry }
    }

    /*fun removeCountry(country: String) {
        _countries.find { it.name.common.lowercase() == country }
    }*/

    /*fun getList(): List<Country> {
        return countries
    }*/

}