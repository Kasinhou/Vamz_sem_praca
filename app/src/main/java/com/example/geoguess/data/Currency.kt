package com.example.geoguess.data

/**
 * Nazov a symbol jednej meny krajiny
 */
data class Currency(val name : String, val symbol : String) {
    /**
     * Textova reprezentacia meny
     */
    fun getCurrency(): String {
        return "$symbol -> $name"
    }
}
