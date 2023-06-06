package com.example.geoguess.activities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.geoguess.data.Country
import com.example.geoguess.data.CountryApi
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CountryInfoViewModel : ViewModel() {
    // The internal MutableLiveData String that stores the most recent response
    private val _response = MutableLiveData<String>()

    // The external immutable LiveData for the response String
    val response: LiveData<String>
        get() = _response

    /**
     * Call getCountriesProperties() on init so we can display status immediately.
     */
    init {
        getCountriesProperties()
    }

    private fun getCountriesProperties() {
        /*_response.value = "Tuto sa to zobraziii"
        CountryApi.retrofitService.getProperties().enqueue(
            object: Callback<List<Country>> {
                override fun onResponse(call: Call<List<Country>>, response: Response<List<Country>>) {
                    _response.value = "SUCCESS: ${response.body()?.get(100)} countries are here."
                    //_response.value = ".................................................................................." + response.body()
                }

                override fun onFailure(call: Call<List<Country>>, t: Throwable) {
                    _response.value = "FAILURE: " + t.message
                }

            }
        )*/
        viewModelScope.launch {
            try {
                val listResult = CountryApi.retrofitService.getProperties()
                _response.value = "SUCCESS: ${listResult.size} countries are here."
            } catch (e: Exception) {
                _response.value = "FAILURE: ${e.message} \nIT IS NOT POSSIBLE TO PLAY!"
            }
        }
    }
}