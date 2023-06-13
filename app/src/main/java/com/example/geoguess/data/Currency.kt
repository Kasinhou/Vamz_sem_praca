package com.example.geoguess.data

data class Currency(val name : String, val symbol : String) {
    fun getCurrency(): String {
        return "$symbol -> $name"
    }
}
